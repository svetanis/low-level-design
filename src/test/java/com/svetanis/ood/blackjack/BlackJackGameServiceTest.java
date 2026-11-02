package com.svetanis.ood.blackjack;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.collect.Lists.shuffle;
import static com.svetanis.java.base.collect.Lists.transform;
import static com.svetanis.java.base.utils.Print.print;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.svetanis.ood.blackjack.model.BlackJackCard;
import com.svetanis.ood.blackjack.model.Card;
import com.svetanis.ood.blackjack.model.Deck;
import com.svetanis.ood.blackjack.model.Hand;
import com.svetanis.ood.blackjack.model.Suit;

public class BlackJackGameServiceTest {

	private final static int PLAYERS = 5;

	@Test
	public void test() {
		try {
			Deck<BlackJackCard> deck = init();
			DeckService<BlackJackCard> ds = new DefaultDeckService<>(deck);
			BlackJackHandService hs = new DefaultBlackJackHandService();
			BlackJackGameService game = new DefaultBlackJackGame(ds, hs);
			play(game);
		} catch (Throwable e) {
			e.printStackTrace();
			throw (e);
		}
	}

	private void play(BlackJackGameService game) {
		List<Hand<BlackJackCard>> hands = game.dealInit(PLAYERS);
		System.out.println("--- Initial ---");
		System.out.println(game.getScores(hands));
		List<Integer> blackjacks = game.getBlackJacks(hands);
		if (blackjacks.size() > 0) {
			System.out.print("Blackjack at ");
			print(blackjacks);
		} else {
			List<Hand<BlackJackCard>> list = game.playAllHands(hands);
			System.out.println("\n--- Completed Game ---");
			System.out.println(game.getScores(list));
			List<Integer> winners = game.getWinners(list);
			if (winners.size() > 0) {
				System.out.print("Winners: ");
				System.out.println(winners);
			} else {
				System.out.println("Draw. All players have busted.");
			}
		}
	}

	private Deck<BlackJackCard> init() {
		List<BlackJackCard> cards = transform(generateCards(), BlackJackCardFunction.INSTANCE);
		List<BlackJackCard> shuffled = shuffle(cards);
		return Deck.build(1, shuffled);
	}

	private ImmutableList<Card> generateCards() {
		List<Card> cards = newArrayList();
		for (int i = 1; i <= 13; ++i) { // ranks 1 (ace) to 13 (king)
			for (Suit suit : Suit.values()) {
				cards.add(Card.build(i, suit));
			}
		}
		return newList(cards);
	}

}
