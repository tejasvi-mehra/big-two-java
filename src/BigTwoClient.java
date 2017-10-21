import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class BigTwoClient implements CardGame, NetworkGame {

	private Deck deck; // a deck of cards
	private ArrayList<CardGamePlayer> playerList; // a list of players
	private ArrayList<Hand> handsOnTable; // a list of hands played on the table
	private int currentIdx; // an integer specifying the index of the current
							// player
	private BigTwoTable table; // a table object for providing the user
								// interface
	private int pass = 0;
	private Card threeOfDiamonds = new Card(0, 2);
	private boolean passed = false; // all 3 passed
	private boolean game = true; // game start
	private boolean first = true; // first
	private boolean legalMove = true; // legal

	private int numOfPlayers;
	private int playerId;
	private String playerName;
	private String serverIP;
	private int serverPort;
	private Socket sock;
	private ObjectOutputStream oos;

	BigTwoClient() {
		playerList = new ArrayList<>();
		handsOnTable = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			playerList.add(new CardGamePlayer());
		}
		table = new BigTwoTable(this);
		// String s = new String();
		// JOptionPane.showInputDialog(s);
		playerName = table.getInputName();
		makeConnection();

	}

	public static Hand composeHand(CardGamePlayer player, CardList cards) {
		if (cards.size() == 1) {
			Single single = new Single(player, cards);
			for (int i = 0; i < cards.size(); i++) {
				single.addCard(cards.getCard(i));
			}
			if (single.isValid()) {
				return single;
			} else
				return null;

		} else if (cards.size() == 2) {

			Pair pair = new Pair(player, cards);
			for (int i = 0; i < cards.size(); i++) {
				pair.addCard(cards.getCard(i));
			}
			if (pair.isValid()) {

				return pair;
			} else
				return null;

		} else if (cards.size() == 3) {
			Triple triple = new Triple(player, cards);
			for (int i = 0; i < cards.size(); i++) {
				triple.addCard(cards.getCard(i));
			}
			if (triple.isValid()) {

				return triple;
			} else
				return null;

		} else if (cards.size() == 5) {
			Quad quad = new Quad(player, cards);
			for (int i = 0; i < cards.size(); i++) {
				quad.addCard(cards.getCard(i));
			}

			Flush flush = new Flush(player, cards);
			for (int i = 0; i < cards.size(); i++) {
				flush.addCard(cards.getCard(i));
			}

			Straight straight = new Straight(player, cards);
			for (int i = 0; i < cards.size(); i++) {
				straight.addCard(cards.getCard(i));
			}

			StraightFlush straightFlush = new StraightFlush(player, cards);
			for (int i = 0; i < cards.size(); i++) {
				straightFlush.addCard(cards.getCard(i));
			}

			FullHouse fullHouse = new FullHouse(player, cards);
			for (int i = 0; i < cards.size(); i++) {
				fullHouse.addCard(cards.getCard(i));
			}

			if (quad.isValid()) {
				return quad;
			}

			else if (straightFlush.isValid()) {
				return straightFlush;
			}

			else if (straight.isValid()) {
				return straight;
			}

			else if (flush.isValid()) {
				return flush;
			}

			else if (fullHouse.isValid()) {
				return fullHouse;
			} else {
				return null;
			}

		} else
			return null;
	}

	public int getNumOfPlayers() {
		return numOfPlayers;
	}

	/**
	 * a method for retrieving the deck of cards being used
	 * 
	 * @return Deck
	 */
	public Deck getDeck() {
		return deck;
	}

	/**
	 * a method for retrieving the list of players
	 * 
	 * @return PlayerList
	 */
	public ArrayList<CardGamePlayer> getPlayerList() {
		return playerList;
	}

	/**
	 * a method for retrieving the list of hands played on the table
	 * 
	 * @return HandsOnTable
	 */
	public ArrayList<Hand> getHandsOnTable() {
		return handsOnTable;
	}

	/**
	 * a method for retrieving the index of the current player
	 * 
	 * @return Current Player Index
	 */
	public int getCurrentIdx() {
		return currentIdx;
	}

	public void start(Deck deck) {

		for (int i = 0; i < numOfPlayers; i++) {
			playerList.get(i).removeAllCards();
		}

		for (int i = 0; i < handsOnTable.size(); i++) {
			handsOnTable.remove(i);
		}

		for (int i = 0; i < 52; i++) {
			for (int j = 0; j < 4; j++) {
				playerList.get(j).addCard(deck.getCard(i++));
			}
			--i;
		}

		for (int i = 0; i < 4; i++) {
			if (playerList.get(i).getCardsInHand().contains(threeOfDiamonds)) {
				currentIdx = i;
				break;
			}
		}

		table.setActivePlayer(this.playerId);
		table.repaint();
	}

	public void makeMove(int playerID, int[] cardIdx) {
		CardGameMessage message = new CardGameMessage(CardGameMessage.MOVE, playerID, cardIdx);
		sendMessage(message);
	}

	/**
	 * a method for checking the move made by the current player given the list
	 * of the indices of the cards selected by him/her.
	 * 
	 * @param cardIdx
	 *            Array of index of selected cards
	 */
	public void checkMove(int playerID, int[] cardIdx) {
		CardList hand = new CardList();
		CardList cardList = new CardList();
		CardGamePlayer player = new CardGamePlayer();
		Hand validHand = null;
		// currentIdx = playerID;
		player = playerList.get(currentIdx);
		table.printMsg("Player " + currentIdx + "'s turn:\n");
		table.repaint();
		cardList = player.getCardsInHand();
		hand = player.play(cardIdx);
		if (currentIdx == playerID) {

			if (hand == null && first == true) {
				legalMove = false;
			} else {
				if (hand == null) {
					pass++;
					table.printMsg("{Pass}\n");
					if (pass == 3) {
						passed = true;
						pass = 0;
						legalMove = true;
					}
					if (currentIdx == 3) {
						currentIdx = 0;
					} else
						currentIdx++;
					table.setActivePlayer(playerId);
					if (passed == true) {
						first = true;
					} else
						first = false;
					passed = false;
				} else {
					validHand = composeHand(player, hand);
					if (validHand != null && (first)) {
						if (game && validHand.contains(threeOfDiamonds)) {
							legalMove = false;
							game = false;
						}
						if (game == false) {
							pass = 0;
							table.printMsg("{" + validHand.getType() + "}" + validHand.toString() + "\n");

							handsOnTable.add(validHand);

							if (currentIdx == 3) {
								currentIdx = 0;
							} else
								currentIdx++;
							table.setActivePlayer(playerId);
							for (int i = 0; i < validHand.size(); i++) {
								cardList.removeCard(validHand.getCard(i));
							}
							legalMove = true;
							if (passed) {
								first = true;
							} else
								first = false;
							passed = false;
						}
					} else if (handsOnTable.size() != 0) {
						if (handsOnTable.get(handsOnTable.size() - 1).size() == validHand.size()
								&& validHand.beats(handsOnTable.get(handsOnTable.size() - 1))) {
							pass = 0;
							table.printMsg("{" + validHand.getType() + "}" + validHand.toString() + "\n");

							handsOnTable.add(validHand);

							if (currentIdx == 3) {
								currentIdx = 0;
							} else
								currentIdx++;
							table.setActivePlayer(playerId);
							for (int i = 0; i < validHand.size(); i++) {
								cardList.removeCard(validHand.getCard(i));
							}
							legalMove = true;
							if (passed) {
								first = true;
							} else
								first = false;
							passed = false;
						} else
							legalMove = false;
					}

					else {
						legalMove = false;
					}
				}
			}
			if (game) {
				legalMove = false;
			}
			if (legalMove == false) {
				if (hand == null)
					table.printMsg("Not a legal move");
				else {
					String send = hand.toString() + "    <=== Not a legal move!!!\n";
					table.printMsg(send);
				}
				legalMove = true;
			}

			if (endOfGame() == true) {
				String endResult = new String();
				endResult = "Game ends\n";
				// table.printMsg();
				for (CardGamePlayer x : playerList) {

					if (x.getNumOfCards() != 0)
						endResult += (x.getName() + " has " + x.getNumOfCards() + " cards in hand.\n");
					// table.printMsg(x.getName() + " has " + x.getNumOfCards()
					// + " cards in hand.\n");
					else
						endResult += (x.getName() + " wins the game.\n");
					// table.printMsg(x.getName() + " wins the game.\n");

				}
				table.endOutput(endResult);
				table.disable();
				for (int i = 0; i < 4; i++) {
					playerList.get(i).removeAllCards();
				}
				handsOnTable.clear();
				table.clearMsgArea();
				CardGameMessage message = new CardGameMessage(CardGameMessage.READY, -1, null);
				sendMessage(message);
			}
		}
		table.resetSelected();
		table.repaint();
	}

	/**
	 * a method for checking if the game ends
	 */
	public boolean endOfGame() {
		int newIndex;
		if (currentIdx == 0)
			newIndex = 3;
		else
			newIndex = currentIdx - 1;
		if (playerList.get(newIndex).getCardsInHand().isEmpty())
			return true;
		return false;
	}

	public int getPlayerID() {
		// TODO Auto-generated method stub
		return playerId;
	}

	public void setPlayerID(int playerID) {
		this.playerId = playerID;

	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getServerIP() {
		return serverIP;
	}

	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public void makeConnection() {
		String IP = table.getIP();
		if (IP == null)
			IP = "127.0.0.1";

		try {
			sock = new Socket(IP, 2396);
			oos = new ObjectOutputStream(sock.getOutputStream());
			Thread readerThread = new Thread(new ServerHandler());
			readerThread.start();
			// system.out.println("Connected");
			CardGameMessage message = new CardGameMessage(CardGameMessage.JOIN, -1, playerName);
			oos.writeObject(message);
			oos.flush();
			message = new CardGameMessage(CardGameMessage.READY, -1, null);
			oos.writeObject(message);
			oos.flush();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public synchronized void parseMessage(GameMessage message) {
		// system.out.println("Parsing");
		int type = message.getType();

		if (type == 1) // Join
		{
			String data = (String) message.getData();
			int id = message.getPlayerID();
			// system.out.println("ID:"+id);
			numOfPlayers++;
			// system.out.println("Num:"+numOfPlayers);
			playerList.get(id).setName(data);
			// table.setActivePlayer(id);
			// 0 table.repaint();
		} else if (type == 2) // Full
		{
			table.printMsg("Server Full\n");
		} else if (type == 3) // Quit
		{
			int id = message.getPlayerID();
			playerList.get(id).setName("");
			numOfPlayers--;
			for (int i = 0; i < playerList.size(); i++) {
				if (playerList.get(i).getName() != null) {
					playerList.get(i).removeAllCards();
				}
			}
			for (int i = 0; i < handsOnTable.size(); i++)
				handsOnTable.remove(i);
			playerList.get(id).removeAllCards();
			CardGameMessage newMessage = new CardGameMessage(CardGameMessage.READY, -1, null);
			table.repaint();
			table.disable();
			sendMessage(newMessage);

		} else if (type == 4) // Ready
		{
			int id = message.getPlayerID();
			table.printMsg("Player " + playerList.get(id).getName() + " is ready\n");
			table.setConnect(false);
		} else if (type == 5) // Start
		{
			System.out.println("Starting");
			pass = 0;
			threeOfDiamonds = new Card(0, 2);
			passed = false; // all 3 passed
			game = true; // game start
			first = true; // first
			legalMove = true; // legal
			for (int i = 0; i < handsOnTable.size(); i++)
				handsOnTable.remove(i);
			handsOnTable.clear();
			table.enable();
			BigTwoDeck deck = (BigTwoDeck) message.getData();
			start(deck);
		} else if (type == 6) // Move
		{
			int id = message.getPlayerID();
			int[] index = (int[]) message.getData();

			checkMove(id, index);
			table.repaint();
		} else if (type == 7) // Msg
		{
			String msg = (String) message.getData();
			table.printChat(msg);
		} else if (type == 0) // PlayerList
		{
			playerId = message.getPlayerID();
			table.setActivePlayer(playerId);
			String[] names = (String[]) message.getData();
			// system.out.println("ajaja");
			for (int i = 0; i < 4; i++) {
				playerList.get(i).setName(names[i]);
			}
			table.repaint();

		}
	}

	public void sendMessage(GameMessage message) {
		try {
			oos.writeObject(message);
			oos.flush();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	class ServerHandler implements Runnable {

		public void run() {
			try {
				ObjectInputStream ois = new ObjectInputStream(sock.getInputStream());
				while (true) {
					parseMessage((CardGameMessage) ois.readObject());
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				table.setConnect(true);
				handsOnTable.clear();
				for (int i = 0; i < playerList.size(); i++) {
					playerList.get(i).removeAllCards();
				}
				table.repaint();
			}
		}
	}

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		BigTwoClient bigTwoClient = new BigTwoClient();
	}

}
