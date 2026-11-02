package com.svetanis.ood.blackjack;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.of;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.java.base.collect.Lists.filter;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.collect.Lists.transform;
import static com.svetanis.java.base.collect.Maps.asMap;

import java.util.List;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.svetanis.java.base.Pair;
import com.svetanis.ood.blackjack.model.BlackJackCard;
import com.svetanis.ood.blackjack.model.Hand;

public final class DefaultBlackJackGame implements BlackJackGameService {

	private static final int MAX = 16;

	public DefaultBlackJackGame(DeckService<BlackJackCard> deck, BlackJackHandService hand) {
		this.deck = checkNotNull(deck, "deck");
		this.hand = checkNotNull(hand, "hand");
	}

	private final DeckService<BlackJackCard> deck;
	private final BlackJackHandService hand;

	@Override
	public ImmutableMap<Integer, Integer> getScores(Iterable<Hand<BlackJackCard>> hands) {
		List<Pair<Integer, Integer>> pairs = transform(hands, h -> Pair.build(h.getId(), hand.score(h)));
		return asMap(pairs);
	}

	@Override
	public ImmutableList<Integer> getWinners(Iterable<Hand<BlackJackCard>> hands) {
		int winningScore = 0;
		List<Integer> winners = newArrayList();
		for (Hand<BlackJackCard> h : hands) {
			if (!hand.busted(h)) {
				if (hand.score(h) > winningScore) {
					winningScore = hand.score(h);
					winners.clear();
					winners.add(h.getId());
				} else if (hand.score(h) == winningScore) {
					winners.add(h.getId());
				}
			}
		}
		return newList(winners);
	}

	@Override
	public ImmutableList<Integer> getBlackJacks(Iterable<Hand<BlackJackCard>> hands) {
		List<Hand<BlackJackCard>> filtered = filter(hands, h -> hand.isBlackJack(h));
		return transform(filtered, h -> h.getId());
	}

	@Override
	public ImmutableList<Hand<BlackJackCard>> dealInit(int players) {
		List<Hand<BlackJackCard>> hands = newArrayList();
		while (players > 0) {
			Optional<Hand<BlackJackCard>> hand = deck.dealHand(players, 2);
			if (hand.isPresent()) {
				hands.add(hand.get());
			}
			players--;
		}
		return newList(hands);
	}

	@Override
	public ImmutableList<Hand<BlackJackCard>> playAllHands(List<Hand<BlackJackCard>> hands) {
		List<Hand<BlackJackCard>> list = newArrayList();
		for (Hand<BlackJackCard> dealt : hands) {
			Optional<Hand<BlackJackCard>> played = playHand(dealt);
			if (played.isPresent()) {
				list.add(played.get()); // KEEP the hand as played, with the cards it drew
			}
		}
		return newList(list);
	}

	private Optional<Hand<BlackJackCard>> playHand(Hand<BlackJackCard> blackJack) {
		while (hand.score(blackJack) < MAX) {
			Optional<BlackJackCard> card = deck.dealCard();
			if (!card.isPresent()) {
				return absent();
			}
			blackJack = hand.add(blackJack, card.get());
		}
		return of(blackJack);
	}

}
