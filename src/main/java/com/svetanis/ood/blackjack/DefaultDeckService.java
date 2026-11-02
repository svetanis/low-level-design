package com.svetanis.ood.blackjack;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.of;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.java.base.collect.Lists.newList;

import java.util.List;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.svetanis.java.base.collect.Lists;
import com.svetanis.ood.blackjack.model.Deck;
import com.svetanis.ood.blackjack.model.Hand;
import com.svetanis.ood.blackjack.model.PlayingCard;

public final class DefaultDeckService<T extends PlayingCard> implements DeckService<T> {

	public DefaultDeckService(Deck<T> deck) {
		this.deck = checkNotNull(deck, "deck");
	}

	private Deck<T> deck;

	@Override
	public ImmutableList<T> shuffle(Iterable<T> cards) {
		return newList(Lists.shuffle(cards));
	}

	@Override
	public Optional<T> dealCard() {
		if (remainingCards(deck) == 0) {
			return absent(); // EMPTY: every card has been dealt
		}
		T card = deck.getCards().get(deck.getDeal() - 1); // TAKE: deal is the 1-based position of the next card
		deck = Deck.build(deck.getDeal() + 1, deck.getCards()); // ADVANCE: the cards stay, only the position moves
		return of(card);
	}

	@Override
	public Optional<Hand<T>> dealHand(int id, int number) {
		if (remainingCards(deck) < number) {
			return absent();
		}
		int count = 0;
		List<T> list = newArrayList();
		while (count < number) {
			Optional<T> card = dealCard();
			if (card.isPresent()) {
				list.add(card.get());
				++count;
			}
		}
		return of(Hand.build(id, list));
	}

	private int remainingCards(Deck<T> deck) {
		return deck.getCards().size() - (deck.getDeal() - 1); // LEFT: positions deal .. size are still in the deck
	}

}
