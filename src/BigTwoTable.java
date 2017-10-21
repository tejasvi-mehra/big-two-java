import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * The BigTwoTable class implements the CardGameTable interface. It is used to build a GUI
 * for the Big Two card game and handle all user actions
 * @author Tejasvi Mehra
 *
 */
public class BigTwoTable implements CardGameTable
{

	private CardGame game;			// a card game associates with this table
	private boolean[] selected; 	// a boolean array indicating which cards are being selected
	private int activePlayer = -1;	// an integer specifying the index of the active player
	private JFrame frame;			// the main window of the application
	private JPanel bigTwoPanel;		// a panel for showing the cards of each player and the cards played on the table
	private JButton playButton;		// a “Play” button for the active player to play the selected cards
	private JButton passButton;		// a “Pass” button for the active player to pass his/her turn to the next player
	private JTextArea msgArea;		// a text area for showing the current game status as well as end of game messages
	private Image[][] cardImages;	// a 2D array storing the images for the faces of the cards
	private Image cardBackImage;	// an image for the backs of the cards
	private Image[] avatars;		// an array storing the images for the avatars
	private JMenu menu;				// a menu to hold menu items
	private JMenu messageMenu;
	private JMenuBar menuBar;		// a bar to hold the menu
	private JMenuItem connect;		// a "connect" button to connect the game
	private JMenuItem quit;			// a "Quit" button to quit the game
	private JMenuItem clearMsg;
	private JMenuItem clearChat;
	private String inputName;
	private String IP;
//	private int port;
	private JTextField outgoing;
	private JButton sendMessage ;
	JTextArea textArea ;


	/**
	 * a constructor for creating a BigTwoTable
	 * @param game Reference to a card game associates with this table
	 */
	public BigTwoTable(CardGame game)
	{
		this.game = game;
		menuBar = new JMenuBar();
		menu = new JMenu("GAME");
		messageMenu = new JMenu("MESSAGE");
		selected = new boolean[13];
		for(int i=0; i< 13; i++)
		{
			selected[i] = false;
		}
		
		JPanel leftFrame = new JPanel();
		JPanel rightFrame = new JPanel();
		
		
		JPanel buttons = new JPanel();
		
		frame = new JFrame("BIG TWO");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setJMenuBar(menuBar);
		connect = new JMenuItem("Connect");
		quit = new JMenuItem("Quit");
		clearMsg = new JMenuItem("Clear Message Area");
		clearChat = new JMenuItem("Clear Chat Area");
		menu.add(connect);
		menu.add(quit);
		menuBar.add(menu);
		messageMenu.add(clearChat);
		messageMenu.add(clearMsg);
		menuBar.add(messageMenu);
		outgoing = new JTextField(50);
		sendMessage = new JButton("Send");
		textArea = new JTextArea();
		textArea.setEditable(false);
		outgoing.addKeyListener(new EnterKeyListener());
		inputName = JOptionPane.showInputDialog(frame, "Input your name:");
		if(inputName == null)
		{
			inputName = "TEJASVI";
		}
//		IPandPort();
		System.out.println("NAME:"+inputName);
		
		
		bigTwoPanel = new BigTwoPanel();
		playButton = new JButton("PLAY");
		playButton.addActionListener(new PlayButtonListener());
		connect.addActionListener(new  ConnectMenuItemListener());
		quit.addActionListener(new  QuitMenuItemListener());
		clearMsg.addActionListener(new ClearMessageListener());
		clearChat.addActionListener(new ClearChatListener());
		
		passButton = new JButton("PASS");
		passButton.addActionListener(new PassButtonListener());
		msgArea = new JTextArea();
		msgArea.setEditable(false);
		frame.setSize(1280, 720);
		JPanel bigPanel = new JPanel();

		bigPanel.setLayout(new GridLayout(1,2));
		leftFrame.setLayout(new BorderLayout());
		bigTwoPanel.setLayout(new GridBagLayout());
		sendMessage.addActionListener(new SendMessageListener());
		
		buttons.add(playButton);
		buttons.add(passButton);
		
		JPanel bottom = new JPanel();
		bottom.setLayout(new GridLayout(1,2));
		bottom.add(buttons);
		JPanel chat = new JPanel();
		chat.add(outgoing);
		chat.add(sendMessage);
		
		bottom.add(chat);
		frame.add(bottom, BorderLayout.SOUTH);
	
		leftFrame.add(bigTwoPanel);
		
		GridBagConstraints c = new GridBagConstraints();	
		c.gridx	= 0;	
		c.gridy	= 0;	
		c.gridwidth	= 1;	//	default	value	
		c.gridheight = 1;	//	default	value
		c.weightx = 0.5;	//	default	value
		c.weighty = 0.5;	//	default	value
		c.anchor = GridBagConstraints.NORTHWEST;	
		c.insets = new	Insets(2,	8,	0,	0);	//	default	value
		c.ipadx	= 0;	//	default	value
		c.ipady	= 0;	//	default	value
		
		avatars = new Image[4];
		avatars[0] = new ImageIcon("Images/batman_64.png").getImage();
		avatars[1] = new ImageIcon("Images/flash_64.png").getImage();
		avatars[2] = new ImageIcon("Images/superman_64.png").getImage();
		avatars[3] = new ImageIcon("Images/green_lantern_64.png").getImage();
		cardBackImage = new ImageIcon("Images/b.gif").getImage();
		
		JLabel player0 = new JLabel("Player 0");
		c.gridx = 0;
		player0.setForeground(Color.BLACK);
		c.gridy = 1;
		c.weighty = 1.0;
		bigTwoPanel.add(player0,c);
		
		JLabel player1 = new JLabel("Player 1");
		player1.setForeground(Color.BLACK);
		c.gridx = 0;
		c.gridy = 2;
		c.weighty = 1.0;
		bigTwoPanel.add(player1,c);
		
		JLabel player2 = new JLabel("Player 2");
		player2.setForeground(Color.BLACK);
		c.gridx = 0;
		c.gridy = 4;
		c.weighty = 1.0;
		c.insets = new Insets(10,10,0,0);
		bigTwoPanel.add(player2,c);
		
		JLabel player3 = new JLabel("Player 3");
		player3.setForeground(Color.BLACK);
		c.gridx = 0;
		c.gridy = 6;
		c.weighty = 2.4;
		bigTwoPanel.add(player3,c);
		
		char[] suits = { 'd', 'c', 'h', 's'};
		char[] ranks = { 'a', '2', '3', '4', '5', '6', '7', '8', '9', 't', 'j', 'q', 'k' };
		cardImages = new Image[4][13];
		for(int i = 0 ;i < 4; i++)
		{
			for(int j=0; j<13;j++)
			{
				cardImages[i][j]=new ImageIcon("Images/"+ranks[j]+suits[i]+".gif").getImage();
			}
		}
//		rightFrame.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
		JScrollPane scroller = new JScrollPane(msgArea);
		JScrollPane newScroller = new JScrollPane(textArea);
		rightFrame.setLayout(new BoxLayout(rightFrame, BoxLayout.Y_AXIS));
		rightFrame.add(scroller);
		rightFrame.add(newScroller);
//		scroller.setBorder(BorderFactory.createLineBorder(Color.BLACK));
//		textArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
//		rightFrame.add(textArea);
		bigPanel.setBackground(new Color(4,158,182));
		bigPanel.add(leftFrame);
		bigPanel.add(rightFrame);
		frame.add(bigPanel);
//		frame.getRootPane().setDefaultButton(new SendMessageListener());
		frame.setVisible(true);
		
	}
	
	public void setConnect(boolean b)
	{
		connect.setEnabled(b);
	}
	
	public void endOutput(String display)
	{
		JOptionPane.showMessageDialog(frame, display);
	}
	
	public String getInputName()
	{
		return inputName;
	}
	
	public String getIP()
	{
		IP = JOptionPane.showInputDialog(frame, "Input IP:");
		return IP;
	}
	
/*	public int getPort()
	{
		return port;
	}
*/
	
	/** 
	 * a method for setting the index of the active player
	 * @param activePlayer Index of active player
	 */
	public void setActivePlayer(int activePlayer) {
		if (activePlayer < 0 || activePlayer >= game.getPlayerList().size()) {
			this.activePlayer = -1;
		} else {
			this.activePlayer = activePlayer;
		}
	}

	/**
	 * a method for getting an array of indices of the cards selected
	 */
	public int[] getSelected() 
	{
		int[] cardIdx = null;
		int count = 0;
		for (int j = 0; j < selected.length; j++) {
			if (selected[j]) {
				count++;
			}
		}

		if (count != 0) {
			cardIdx = new int[count];
			count = 0;
			for (int j = 0; j < selected.length; j++) {
				if (selected[j]) {
					cardIdx[count] = j;
					count++;
				}
			}
		}
		
		return cardIdx;
	}
	
	/**
	 * a method for resetting the list of selected cards 
	 */
	public void resetSelected()
	{
		for(int i=0;i<13;i++)
			selected[i]=false;
	}
	
	/** 
	 * a method for repainting the GUI	 
	 */
	public void repaint() 
	{
		frame.repaint();
	}

	/**
	 * a method for printing the specified string to the message area of the GUI
	 * @param msg Specified message to print
	 */
	public void printMsg(String msg) 
	{
		msgArea.append(msg);
		frame.repaint();
	}

	public void printChat(String msg)
	{
		textArea.append(msg+"\n");
		frame.repaint();
	}
	
	/**
	 * a method for clearing the message area of the GUI
	 */
	public void clearMsgArea() 
	{	
		msgArea.setText(null);
	}
	
	/**
	 *  a method for resetting the GUI
	 */
	public void reset() 
	{
		BigTwoDeck d = new BigTwoDeck();
		d.initialize();
		d.shuffle();
		BigTwo b = (BigTwo)game;
		b.resetBigTwo(d);
	}
	
	/**
	 * a method for enabling user interactions with the GUI
	 */
	public void enable() 
	{	
		playButton.setEnabled(true);
		passButton.setEnabled(true);
	}
	
	/**
	 * a method for disabling user interactions with the GUI
	 */
	public void disable() 
	{
		playButton.setEnabled(false);
		passButton.setEnabled(false);
	}
	
	/**
	 * an inner class that extends the JPanel class and implements the MouseListener interface
	 * @author Tejasvi Mehra
	 *
	 */
	class BigTwoPanel extends JPanel implements MouseListener
	{

		private static final long serialVersionUID = 1L;

		/**
		 * Constructor of BigTwoPanel 
		 */
		public BigTwoPanel()
		{
			this.addMouseListener(this);
		}
		
		/**
		 * Overridden method inherited from the JPanel class to draw the card game table
		 */
		public void paintComponent(Graphics g)
		{
			g.drawLine(0, 0, 700, 0);
			g.drawLine(0, 115, 700, 115);
			g.drawLine(0, 242, 700, 242);
			g.drawLine(0, 365, 700, 365);
			g.drawLine(0, 491, 700, 491);
			g.setColor(Color.BLACK);
			g.drawImage(avatars[0], 0, 30, this);
			g.drawImage(avatars[1], 0, 155, this);
			g.drawImage(avatars[2], 0, 280, this);
			g.drawImage(avatars[3], 0, 405, this);
//			g.drawImage(cardImages[1][1], 70, 15, this);
			if(activePlayer!= -1){
//			System.out.println("active: "+ activePlayer);
				if(game.getPlayerList().get(activePlayer).getName()!=null)
			{

			for(int i=0;i<game.getPlayerList().get(activePlayer).getNumOfCards();i++)
			{
								int rank = game.getPlayerList().get(activePlayer).getCardsInHand().getCard(i).getRank();
				int suit = game.getPlayerList().get(activePlayer).getCardsInHand().getCard(i).getSuit();
				
				if(selected[i] == true)
				{
					g.drawImage(cardImages[suit][rank],(70+i*20),(15+125*activePlayer-15),this);
				}
				else
				{
					g.drawImage(cardImages[suit][rank],(70+i*20),(15+125*activePlayer),this);
				}
			}
			for(int i=0;i<4;i++)
			{
				for(int j=0;j<game.getPlayerList().get(i).getNumOfCards();j++)
				{
					if(i!=activePlayer)
					{
						g.drawImage(cardBackImage, (70+j*20),(15+125*i),this);
					}
				}
			}
			
			if(game.getHandsOnTable().size()!=0)
			{
				String play = ("Played by "+game.getHandsOnTable().get(game.getHandsOnTable().size()-1).getPlayer().getName()+":");
				g.setFont(new Font("TimesNewRoman", Font.BOLD, 12));
				g.drawString(play, 10 , 510);
				
				for(int i=0;i<game.getHandsOnTable().get(game.getHandsOnTable().size()-1).size();i++)
				{
					int rank = game.getHandsOnTable().get(game.getHandsOnTable().size()-1).getCard(i).getRank();
					int suit = game.getHandsOnTable().get(game.getHandsOnTable().size()-1).getCard(i).getSuit();
					g.drawImage(cardImages[suit][rank],(70+i*20),(15+125*4),this);
				}
			}
			}
			}
		}

		/**
		 * Implemented method from the MouseListener interface to handle mouse click events
		 */
		public void mouseClicked(MouseEvent e)
		{
			int x,y;
			System.out.println(activePlayer+ "  play");
			x=e.getX();
			y=e.getY();
			
			for(int i = game.getPlayerList().get(activePlayer).getNumOfCards()-1 ; i>=0;i--)
			{
				if((x>=70+i*20 && x<=70+i*20+20) && (y>=15+125*activePlayer && y<=15+125*activePlayer+95) && selected[i] == false)
				{
					
					selected[i]=true;
					break;
				}
				else if((x>=70+i*20 && x<=70+i*20+80) && (y>=15+125*activePlayer-15 && y<=15+125*activePlayer+95-15) && selected[i] == true)
				{
					selected[i]=false;
					break;
				}
				if(x>=(game.getPlayerList().get(activePlayer).getNumOfCards()-1)*20+70 && x<=70+(game.getPlayerList().get(activePlayer).getNumOfCards()-1)*20+90 && (y>=15+125*activePlayer && y<=15+125*activePlayer+95) && selected[game.getPlayerList().get(activePlayer).getNumOfCards()-1] == false)
				{
					selected[game.getPlayerList().get(activePlayer).getNumOfCards()-1] = true;
					break;
				}
			}
			frame.repaint();
		}
		
		/**
		 * Implemented method not used
		 */
		public void mouseEntered(MouseEvent arg0) {
		}
		
		/**
		 * Implemented method not used
		 */
		public void mouseExited(MouseEvent arg0) {
		}
		
		/**
		 * Implemented method not used
		 */
		public void mousePressed(MouseEvent arg0) {
		}
		
		/**
		 * Implemented method not used
		 */
		public void mouseReleased(MouseEvent arg0) {
		}
		
	}

	/**
	 * an inner class that implements the ActionListener interface for playButton
	 * @author Tejasvi Mehra
	 *
	 */
	class PlayButtonListener implements ActionListener
	{
		/**
		 * Implemented method from the ActionListener interface to handle button-click events for the “Play” button.
		 */
		public void actionPerformed(ActionEvent e) 
		{
			int[] cardIdx = getSelected();
			if(cardIdx == null)
				return;
			
			
			game.makeMove(activePlayer, cardIdx);
			
			resetSelected();
			frame.repaint();
		}
		
	}
	
	/**
	 * an inner class that implements the ActionListener interface for passButton
	 * @author Tejasvi Mehrs
	 *
	 */
	class PassButtonListener implements ActionListener
	{
		/**
		 * Implements method from the ActionListener interface to handle button-click events for the “Pass” button
		 */
		public void actionPerformed(ActionEvent e)
		{
			game.makeMove(activePlayer, null);
			resetSelected();
			frame.repaint();
		}
		
	}
	
	/**
	 * an inner class that implements the ActionListener interface for connect menu item
	 * @author Tejasvi Mehra
	 *
	 */
	class  ConnectMenuItemListener implements ActionListener
	{
		/**
		 * Implemented method from the ActionListener interface to handle menu-item-click events for the “connect” menu item
		 */
		public void actionPerformed(ActionEvent e)
		{
			((BigTwoClient)game).makeConnection();
		}
	}
	
	/**
	 * an inner class that implements the ActionListener interface for Quit menu item
	 * @author Tejasvi Mehra
	 *
	 */
	class  QuitMenuItemListener implements ActionListener
	{
		/**
		 * Implemented method from the ActionListener interface to handle menu-item-click events for the “Quit” menu item
		 */
		public void actionPerformed(ActionEvent e)
		{
			System.exit(0);
		}
	}
	
	class SendMessageListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			CardGameMessage message = new CardGameMessage(CardGameMessage.MSG,-1, outgoing.getText());
			((BigTwoClient)game).sendMessage(message);
			outgoing.setText("");
		}
	}
	
	class EnterKeyListener implements KeyListener
	{

		@Override
		public void keyPressed(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			if(arg0.getKeyCode() == KeyEvent.VK_ENTER)
			{
				CardGameMessage message = new CardGameMessage(CardGameMessage.MSG,-1, outgoing.getText());
				((BigTwoClient)game).sendMessage(message);
				outgoing.setText("");
				
			}
			
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	class ClearMessageListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			clearMsgArea();
		}
		
	}
	
	class ClearChatListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			textArea.setText("");
		}
		
	}
}