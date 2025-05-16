//package tripleo.elijah_fluffy.viz;
//
//import org.apache.log4j.Level;
//import org.apache.log4j.Logger;
//import org.apache.log4j.Priority;
//import org.apache.log4j.PropertyConfigurator;
//import org.apache.log4j.net.HardenedLoggingEventInputStream;
//import org.apache.log4j.spi.LoggingEvent;
//import org.xml.sax.Attributes;
//import org.xml.sax.InputSource;
//import org.xml.sax.SAXException;
//import org.xml.sax.XMLReader;
//import org.xml.sax.helpers.DefaultHandler;
//
//import javax.swing.*;
//import javax.swing.event.DocumentEvent;
//import javax.swing.event.DocumentListener;
//import javax.swing.event.ListSelectionEvent;
//import javax.swing.event.ListSelectionListener;
//import javax.swing.table.AbstractTableModel;
//import javax.xml.parsers.ParserConfigurationException;
//import javax.xml.parsers.SAXParserFactory;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.WindowAdapter;
//import java.awt.event.WindowEvent;
//import java.io.EOFException;
//import java.io.File;
//import java.io.IOException;
//import java.io.StringReader;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.net.SocketException;
//import java.text.DateFormat;
//import java.text.MessageFormat;
//import java.util.*;
//import java.util.List;
//
//public class VizMain extends JFrame {
//	/**
//	 * the default port number to listen on
//	 **/
//	private static final int DEFAULT_PORT = 4445;
//
//	/**
//	 * name of property for port name
//	 **/
//	public static final String PORT_PROP_NAME = "chainsaw.port";
//
//	/**
//	 * use to log messages
//	 **/
//	private static final Logger LOG = Logger.getLogger(VizMain.class);
//
//	/**
//	 * Creates a new <code>Main</code> instance.
//	 */
//	private VizMain() {
//		super("CHAINSAW - Log4J Log Viewer");
//		// create the all important model
//		final MyTableModel model = new MyTableModel();
//
//		// Create the menu bar.
//		final JMenuBar menuBar = new JMenuBar();
//		setJMenuBar(menuBar);
//		final JMenu menu = new JMenu("File");
//		menuBar.add(menu);
//
//		try {
//			final LoadXMLAction lxa          = new LoadXMLAction(this, model);
//			final JMenuItem     loadMenuItem = new JMenuItem("Load file...");
//			menu.add(loadMenuItem);
//			loadMenuItem.addActionListener(lxa);
//		} catch (NoClassDefFoundError e) {
//			LOG.info("Missing classes for XML parser", e);
//			JOptionPane.showMessageDialog(this, "XML parser not in classpath - unable to load XML events.", "CHAINSAW",
//			                              JOptionPane.ERROR_MESSAGE);
//		} catch (Exception e) {
//			LOG.info("Unable to create the action to load XML files", e);
//			JOptionPane.showMessageDialog(this, "Unable to create a XML parser - unable to load XML events.",
//			                              "CHAINSAW", JOptionPane.ERROR_MESSAGE);
//		}
//
//		final JMenuItem exitMenuItem = new JMenuItem("Exit");
//		menu.add(exitMenuItem);
//		exitMenuItem.addActionListener(ExitAction.INSTANCE);
//
//		// Add control panel
//		final ControlPanel cp = new ControlPanel(model);
//		getContentPane().add(cp, BorderLayout.NORTH);
//
//		// Create the table
//		final JTable table = new JTable(model);
//		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//		final JScrollPane scrollPane = new JScrollPane(table);
//		scrollPane.setBorder(BorderFactory.createTitledBorder("Events: "));
//		scrollPane.setPreferredSize(new Dimension(900, 300));
//
//		// Create the details
//		final JPanel details = new DetailPanel(table, model);
//		details.setPreferredSize(new Dimension(900, 300));
//
//		// Add the table and stack trace into a splitter
//		final JSplitPane jsp = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollPane, details);
//		getContentPane().add(jsp, BorderLayout.CENTER);
//
//		addWindowListener(new WindowAdapter() {
//			public void windowClosing(WindowEvent aEvent) {
//				ExitAction.INSTANCE.actionPerformed(null);
//			}
//		});
//
//		pack();
//		setVisible(true);
//
//		setupReceiver(model);
//	}
//
//	/**
//	 * Setup recieving messages.
//	 *
//	 * @param aModel a <code>MyTableModel</code> value
//	 */
//	private void setupReceiver(MyTableModel aModel) {
//		int port = DEFAULT_PORT;
//		final String strRep = System.getProperty(PORT_PROP_NAME);
//		if (strRep != null) {
//			try {
//				port = Integer.parseInt(strRep);
//			} catch (NumberFormatException nfe) {
//				LOG.fatal("Unable to parse " + PORT_PROP_NAME + " property with value " + strRep + ".");
//				JOptionPane.showMessageDialog(this, "Unable to parse port number from '" + strRep + "', quitting.",
//				                              "CHAINSAW", JOptionPane.ERROR_MESSAGE);
//				System.exit(1);
//			}
//		}
//
//		try {
//			final LoggingReceiver lr = new LoggingReceiver(aModel, port);
//			lr.start();
//		} catch (IOException e) {
//			LOG.fatal("Unable to connect to socket server, quiting", e);
//			JOptionPane.showMessageDialog(this, "Unable to create socket on port " + port + ", quitting.", "CHAINSAW",
//			                              JOptionPane.ERROR_MESSAGE);
//			System.exit(1);
//		}
//	}
//
//	////////////////////////////////////////////////////////////////////////////
//	// static methods
//	////////////////////////////////////////////////////////////////////////////
//
//	/**
//	 * initialise log4j
//	 **/
//	private static void initLog4J() {
//		final Properties props = new Properties();
//		props.setProperty("log4j.rootLogger", "DEBUG, A1");
//		props.setProperty("log4j.appender.A1", "org.apache.log4j.ConsoleAppender");
//		props.setProperty("log4j.appender.A1.layout", "org.apache.log4j.TTCCLayout");
//		PropertyConfigurator.configure(props);
//	}
//
//	/**
//	 * The main method.
//	 *
//	 * @param aArgs ignored
//	 */
//	public static void main(String[] aArgs) {
//		initLog4J();
//		new VizMain();
//	}
//
//	class ControlPanel extends JPanel {
//		/**
//		 * use the log messages
//		 **/
//		private static final Logger LOG = Logger.getLogger(ControlPanel.class);
//
//		/**
//		 * Creates a new <code>ControlPanel</code> instance.
//		 *
//		 * @param aModel the model to control
//		 */
//		ControlPanel(final MyTableModel aModel) {
//			setBorder(BorderFactory.createTitledBorder("Controls: "));
//			final GridBagLayout gridbag = new GridBagLayout();
//			final GridBagConstraints c = new GridBagConstraints();
//			setLayout(gridbag);
//
//			// Pad everything
//			c.ipadx = 5;
//			c.ipady = 5;
//
//			// Add the 1st column of labels
//			c.gridx = 0;
//			c.anchor = GridBagConstraints.EAST;
//
//			c.gridy = 0;
//			JLabel label = new JLabel("Filter Level:");
//			gridbag.setConstraints(label, c);
//			add(label);
//
//			c.gridy++;
//			label = new JLabel("Filter Thread:");
//			gridbag.setConstraints(label, c);
//			add(label);
//
//			c.gridy++;
//			label = new JLabel("Filter Logger:");
//			gridbag.setConstraints(label, c);
//			add(label);
//
//			c.gridy++;
//			label = new JLabel("Filter NDC:");
//			gridbag.setConstraints(label, c);
//			add(label);
//
//			c.gridy++;
//			label = new JLabel("Filter Message:");
//			gridbag.setConstraints(label, c);
//			add(label);
//
//			// Add the 2nd column of filters
//			c.weightx = 1;
//			// c.weighty = 1;
//			c.gridx = 1;
//			c.anchor = GridBagConstraints.WEST;
//
//			c.gridy = 0;
//			final Level[] allPriorities = new Level[] { Level.FATAL, Level.ERROR, Level.WARN, Level.INFO, Level.DEBUG,
//					Level.TRACE };
//
//			final JComboBox priorities = new JComboBox(allPriorities);
//			final Level lowest = allPriorities[allPriorities.length - 1];
//			priorities.setSelectedItem(lowest);
//			aModel.setPriorityFilter(lowest);
//			gridbag.setConstraints(priorities, c);
//			add(priorities);
//			priorities.setEditable(false);
//			priorities.addActionListener(new ActionListener() {
//				public void actionPerformed(ActionEvent aEvent) {
//					aModel.setPriorityFilter((Priority) priorities.getSelectedItem());
//				}
//			});
//
//			c.fill = GridBagConstraints.HORIZONTAL;
//			c.gridy++;
//			final JTextField threadField = new JTextField("");
//			threadField.getDocument().addDocumentListener(new DocumentListener() {
//				public void insertUpdate(DocumentEvent aEvent) {
//					aModel.setThreadFilter(threadField.getText());
//				}
//
//				public void removeUpdate(DocumentEvent aEvente) {
//					aModel.setThreadFilter(threadField.getText());
//				}
//
//				public void changedUpdate(DocumentEvent aEvent) {
//					aModel.setThreadFilter(threadField.getText());
//				}
//			});
//			gridbag.setConstraints(threadField, c);
//			add(threadField);
//
//			c.gridy++;
//			final JTextField catField = new JTextField("");
//			catField.getDocument().addDocumentListener(new DocumentListener() {
//				public void insertUpdate(DocumentEvent aEvent) {
//					aModel.setCategoryFilter(catField.getText());
//				}
//
//				public void removeUpdate(DocumentEvent aEvent) {
//					aModel.setCategoryFilter(catField.getText());
//				}
//
//				public void changedUpdate(DocumentEvent aEvent) {
//					aModel.setCategoryFilter(catField.getText());
//				}
//			});
//			gridbag.setConstraints(catField, c);
//			add(catField);
//
//			c.gridy++;
//			final JTextField ndcField = new JTextField("");
//			ndcField.getDocument().addDocumentListener(new DocumentListener() {
//				public void insertUpdate(DocumentEvent aEvent) {
//					aModel.setNDCFilter(ndcField.getText());
//				}
//
//				public void removeUpdate(DocumentEvent aEvent) {
//					aModel.setNDCFilter(ndcField.getText());
//				}
//
//				public void changedUpdate(DocumentEvent aEvent) {
//					aModel.setNDCFilter(ndcField.getText());
//				}
//			});
//			gridbag.setConstraints(ndcField, c);
//			add(ndcField);
//
//			c.gridy++;
//			final JTextField msgField = new JTextField("");
//			msgField.getDocument().addDocumentListener(new DocumentListener() {
//				public void insertUpdate(DocumentEvent aEvent) {
//					aModel.setMessageFilter(msgField.getText());
//				}
//
//				public void removeUpdate(DocumentEvent aEvent) {
//					aModel.setMessageFilter(msgField.getText());
//				}
//
//				public void changedUpdate(DocumentEvent aEvent) {
//					aModel.setMessageFilter(msgField.getText());
//				}
//			});
//
//			gridbag.setConstraints(msgField, c);
//			add(msgField);
//
//			// Add the 3rd column of buttons
//			c.weightx = 0;
//			c.fill = GridBagConstraints.HORIZONTAL;
//			c.anchor = GridBagConstraints.EAST;
//			c.gridx = 2;
//
//			c.gridy = 0;
//			final JButton exitButton = new JButton("Exit");
//			exitButton.setMnemonic('x');
//			exitButton.addActionListener(ExitAction.INSTANCE);
//			gridbag.setConstraints(exitButton, c);
//			add(exitButton);
//
//			c.gridy++;
//			final JButton clearButton = new JButton("Clear");
//			clearButton.setMnemonic('c');
//			clearButton.addActionListener(new ActionListener() {
//				public void actionPerformed(ActionEvent aEvent) {
//					aModel.clear();
//				}
//			});
//			gridbag.setConstraints(clearButton, c);
//			add(clearButton);
//
//			c.gridy++;
//			final JButton toggleButton = new JButton("Pause");
//			toggleButton.setMnemonic('p');
//			toggleButton.addActionListener(new ActionListener() {
//				public void actionPerformed(ActionEvent aEvent) {
//					aModel.toggle();
//					toggleButton.setText(aModel.isPaused() ? "Resume" : "Pause");
//				}
//			});
//			gridbag.setConstraints(toggleButton, c);
//			add(toggleButton);
//		}
//	}
//
//	class DetailPanel extends JPanel implements ListSelectionListener {
//		/**
//		 * used to log events
//		 **/
//		private static final Logger LOG = Logger.getLogger(DetailPanel.class);
//
//		/**
//		 * used to format the logging event
//		 **/
//		private static final MessageFormat FORMATTER = new MessageFormat(
//				"<b>Time:</b> <code>{0,time,medium}</code>" + "&nbsp;&nbsp;<b>Priority:</b> <code>{1}</code>"
//						+ "&nbsp;&nbsp;<b>Thread:</b> <code>{2}</code>" + "&nbsp;&nbsp;<b>NDC:</b> <code>{3}</code>"
//						+ "<br><b>Logger:</b> <code>{4}</code>" + "<br><b>Location:</b> <code>{5}</code>"
//						+ "<br><b>Message:</b>" + "<pre>{6}</pre>" + "<b>Throwable:</b>" + "<pre>{7}</pre>");
//
//		/**
//		 * the model for the data to render
//		 **/
//		private final MyTableModel mModel;
//		/**
//		 * pane for rendering detail
//		 **/
//		private final JEditorPane mDetails;
//
//		/**
//		 * Creates a new <code>DetailPanel</code> instance.
//		 *
//		 * @param aTable the table to listen for selections on
//		 * @param aModel the model backing the table
//		 */
//		DetailPanel(JTable aTable, final MyTableModel aModel) {
//			mModel = aModel;
//			setLayout(new BorderLayout());
//			setBorder(BorderFactory.createTitledBorder("Details: "));
//
//			mDetails = new JEditorPane();
//			mDetails.setEditable(false);
//			mDetails.setContentType("text/html");
//			add(new JScrollPane(mDetails), BorderLayout.CENTER);
//
//			final ListSelectionModel rowSM = aTable.getSelectionModel();
//			rowSM.addListSelectionListener(this);
//		}
//
//		/**
//		 * @see ListSelectionListener
//		 **/
//		public void valueChanged(ListSelectionEvent aEvent) {
//			// Ignore extra messages.
//			if (aEvent.getValueIsAdjusting()) {
//				return;
//			}
//
//			final ListSelectionModel lsm = (ListSelectionModel) aEvent.getSource();
//			if (lsm.isSelectionEmpty()) {
//				mDetails.setText("Nothing selected");
//			} else {
//				final int          selectedRow = lsm.getMinSelectionIndex();
//				final EventDetails e           = mModel.getEventDetails(selectedRow);
//				final Object[] args = { new Date(e.getTimeStamp()), e.getPriority(), escape(e.getThreadName()),
//						escape(e.getNDC()), escape(e.getCategoryName()), escape(e.getLocationDetails()),
//						escape(e.getMessage()), escape(getThrowableStrRep(e)) };
//				mDetails.setText(FORMATTER.format(args));
//				mDetails.setCaretPosition(0);
//			}
//		}
//
//		////////////////////////////////////////////////////////////////////////////
//		// Private methods
//		////////////////////////////////////////////////////////////////////////////
//
//		/**
//		 * Returns a string representation of a throwable.
//		 *
//		 * @param aEvent contains the throwable information
//		 * @return a <code>String</code> value
//		 */
//		private static String getThrowableStrRep(EventDetails aEvent) {
//			final String[] strs = aEvent.getThrowableStrRep();
//			if (strs == null) {
//				return null;
//			}
//
//			final StringBuilder sb = new StringBuilder();
//			for (int i = 0; i < strs.length; i++) {
//				sb.append(strs[i]).append("\n");
//			}
//
//			return sb.toString();
//		}
//
//		/**
//		 * Escape &lt;, &gt; &amp; and &quot; as their entities. It is very dumb about &amp; handling.
//		 *
//		 * @param aStr the String to escape.
//		 * @return the escaped String
//		 */
//		private String escape(String aStr) {
//			if (aStr == null) {
//				return null;
//			}
//
//			final StringBuilder buf = new StringBuilder();
//			for (int i = 0; i < aStr.length(); i++) {
//				char c = aStr.charAt(i);
//				switch (c) {
//				case '<':
//					buf.append("&lt;");
//					break;
//				case '>':
//					buf.append("&gt;");
//					break;
//				case '\"':
//					buf.append("&quot;");
//					break;
//				case '&':
//					buf.append("&amp;");
//					break;
//				default:
//					buf.append(c);
//					break;
//				}
//			}
//			return buf.toString();
//		}
//	}
//
//	class EventDetails {
//
//		/**
//		 * the time of the event
//		 **/
//		private final long mTimeStamp;
//		/**
//		 * the priority of the event
//		 **/
//		private final Priority mPriority;
//		/**
//		 * the category of the event
//		 **/
//		private final String mCategoryName;
//		/**
//		 * the NDC for the event
//		 **/
//		private final String mNDC;
//		/**
//		 * the thread for the event
//		 **/
//		private final String mThreadName;
//		/**
//		 * the msg for the event
//		 **/
//		private final String mMessage;
//		/**
//		 * the throwable details the event
//		 **/
//		private final String[] mThrowableStrRep;
//		/**
//		 * the location details for the event
//		 **/
//		private final String mLocationDetails;
//
//		/**
//		 * Creates a new <code>EventDetails</code> instance.
//		 *
//		 * @param aTimeStamp       a <code>long</code> value
//		 * @param aPriority        a <code>Priority</code> value
//		 * @param aCategoryName    a <code>String</code> value
//		 * @param aNDC             a <code>String</code> value
//		 * @param aThreadName      a <code>String</code> value
//		 * @param aMessage         a <code>String</code> value
//		 * @param aThrowableStrRep a <code>String[]</code> value
//		 * @param aLocationDetails a <code>String</code> value
//		 */
//		EventDetails(long aTimeStamp, Priority aPriority, String aCategoryName, String aNDC, String aThreadName,
//		             String aMessage, String[] aThrowableStrRep, String aLocationDetails) {
//			mTimeStamp = aTimeStamp;
//			mPriority = aPriority;
//			mCategoryName = aCategoryName;
//			mNDC = aNDC;
//			mThreadName = aThreadName;
//			mMessage = aMessage;
//			mThrowableStrRep = aThrowableStrRep;
//			mLocationDetails = aLocationDetails;
//		}
//
//		/**
//		 * Creates a new <code>EventDetails</code> instance.
//		 *
//		 * @param aEvent a <code>LoggingEvent</code> value
//		 */
//		EventDetails(LoggingEvent aEvent) {
//
//			this(aEvent.timeStamp, aEvent.getLevel(), aEvent.getLoggerName(), aEvent.getNDC(), aEvent.getThreadName(),
//			     aEvent.getRenderedMessage(), aEvent.getThrowableStrRep(),
//			     (aEvent.getLocationInformation() == null) ? null : aEvent.getLocationInformation().fullInfo);
//		}
//
//		/**
//		 * @see #mTimeStamp
//		 **/
//		long getTimeStamp() {
//			return mTimeStamp;
//		}
//
//		/**
//		 * @see #mPriority
//		 **/
//		Priority getPriority() {
//			return mPriority;
//		}
//
//		/**
//		 * @see #mCategoryName
//		 **/
//		String getCategoryName() {
//			return mCategoryName;
//		}
//
//		/**
//		 * @see #mNDC
//		 **/
//		String getNDC() {
//			return mNDC;
//		}
//
//		/**
//		 * @see #mThreadName
//		 **/
//		String getThreadName() {
//			return mThreadName;
//		}
//
//		/**
//		 * @see #mMessage
//		 **/
//		String getMessage() {
//			return mMessage;
//		}
//
//		/**
//		 * @see #mLocationDetails
//		 **/
//		String getLocationDetails() {
//			return mLocationDetails;
//		}
//
//		/**
//		 * @see #mThrowableStrRep
//		 **/
//		String[] getThrowableStrRep() {
//			return mThrowableStrRep;
//		}
//	}
//
//	static class ExitAction extends AbstractAction {
//		/**
//		 * use to log messages
//		 **/
//		private static final Logger                               LOG      = Logger.getLogger(ExitAction.class);
//		/**
//		 * The instance to share
//		 **/
//		public static final  ExitAction INSTANCE = new ExitAction();
//
//		/**
//		 * Stop people creating instances
//		 **/
//		private ExitAction() {
//		}
//
//		/**
//		 * Will shutdown the application.
//		 *
//		 * @param aIgnore ignored
//		 */
//		public void actionPerformed(ActionEvent aIgnore) {
//			LOG.info("shutting down");
//			System.exit(0);
//		}
//	}
//
//	class LoadXMLAction extends AbstractAction {
//		/**
//		 * use to log messages
//		 **/
//		private static final Logger LOG = Logger.getLogger(LoadXMLAction.class);
//
//		/**
//		 * the parent frame
//		 **/
//		private final JFrame mParent;
//
//		/**
//		 * the file chooser - configured to allow only the selection of a single file.
//		 */
//		private final JFileChooser mChooser = new JFileChooser();
//
//		{
//			mChooser.setMultiSelectionEnabled(false);
//			mChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
//		}
//
//		/**
//		 * parser to read XML files
//		 **/
//		private final XMLReader      mParser;
//		/**
//		 * the content handler
//		 **/
//		private final XMLFileHandler mHandler;
//
//		/**
//		 * Creates a new <code>LoadXMLAction</code> instance.
//		 *
//		 * @param aParent the parent frame
//		 * @param aModel  the model to add events to
//		 * @throws SAXException                 if an error occurs
//		 * @throws ParserConfigurationException if an error occurs
//		 */
//		LoadXMLAction(JFrame aParent, MyTableModel aModel) throws SAXException, ParserConfigurationException {
//			mParent = aParent;
//			mHandler = new XMLFileHandler(aModel);
//			SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
//			// prevent XXE attacks
//			saxParserFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
//			saxParserFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
//			mParser = saxParserFactory.newSAXParser().getXMLReader();
//			mParser.setContentHandler(mHandler);
//		}
//
//		/**
//		 * Prompts the user for a file to load events from.
//		 *
//		 * @param aIgnore an <code>ActionEvent</code> value
//		 */
//		public void actionPerformed(ActionEvent aIgnore) {
//			LOG.info("load file called");
//			if (mChooser.showOpenDialog(mParent) == JFileChooser.APPROVE_OPTION) {
//				LOG.info("Need to load a file");
//				final File chosen = mChooser.getSelectedFile();
//				LOG.info("loading the contents of " + chosen.getAbsolutePath());
//				try {
//					final int num = loadFile(chosen.getAbsolutePath());
//					JOptionPane.showMessageDialog(mParent, "Loaded " + num + " events.", "CHAINSAW",
//					                              JOptionPane.INFORMATION_MESSAGE);
//				} catch (Exception e) {
//					LOG.warn("caught an exception loading the file", e);
//					JOptionPane.showMessageDialog(mParent, "Error parsing file - " + e.getMessage(), "CHAINSAW",
//					                              JOptionPane.ERROR_MESSAGE);
//				}
//			}
//		}
//
//		/**
//		 * Loads the contents of file into the model
//		 *
//		 * @param aFile the file to extract events from
//		 * @return the number of events loaded
//		 * @throws SAXException if an error occurs
//		 * @throws IOException  if an error occurs
//		 */
//		private int loadFile(String aFile) throws SAXException, IOException {
//			synchronized (mParser) {
//				// Create a dummy document to parse the file
//				final StringBuilder buf = new StringBuilder();
//				buf.append("<?xml version=\"1.0\" standalone=\"yes\"?>\n");
//				buf.append("<!DOCTYPE log4j:eventSet ");
//				buf.append("[<!ENTITY data SYSTEM \"file:///");
//				buf.append(aFile);
//				buf.append("\">]>\n");
//				buf.append("<log4j:eventSet xmlns:log4j=\"Claira\">\n");
//				buf.append("&data;\n");
//				buf.append("</log4j:eventSet>\n");
//
//				final InputSource is = new InputSource(new StringReader(buf.toString()));
//				mParser.parse(is);
//				return mHandler.getNumEvents();
//			}
//		}
//	}
//
//	class LoggingReceiver extends Thread {
//		/**
//		 * used to log messages
//		 **/
//		private static final Logger LOG = Logger.getLogger(LoggingReceiver.class);
//
//		/**
//		 * Helper that actually processes a client connection. It receives events and adds them to the supplied model.
//		 *
//		 * @author <a href="mailto:oliver@puppycrawl.com">Oliver Burn</a>
//		 */
//		private class Slurper implements Runnable {
//			/**
//			 * socket connection to read events from
//			 **/
//			private final Socket mClient;
//
//			/**
//			 * Creates a new <code>Slurper</code> instance.
//			 *
//			 * @param aClient socket to receive events from
//			 */
//			Slurper(Socket aClient) {
//				mClient = aClient;
//			}
//
//			/**
//			 * loops getting the events
//			 **/
//			public void run() {
//				LOG.debug("Starting to get data");
//				try {
//					final HardenedLoggingEventInputStream hleis = new HardenedLoggingEventInputStream(
//							mClient.getInputStream());
//					while (true) {
//						final LoggingEvent event = (LoggingEvent) hleis.readObject();
//						mModel.addEvent(new EventDetails(event));
//					}
//				} catch (EOFException e) {
//					LOG.info("Reached EOF, closing connection");
//				} catch (SocketException e) {
//					LOG.info("Caught SocketException, closing connection");
//				} catch (IOException e) {
//					LOG.warn("Got IOException, closing connection", e);
//				} catch (ClassNotFoundException e) {
//					LOG.warn("Got ClassNotFoundException, closing connection", e);
//				}
//
//				try {
//					mClient.close();
//				} catch (IOException e) {
//					LOG.warn("Error closing connection", e);
//				}
//			}
//		}
//
//		/**
//		 * where to put the events
//		 **/
//		private MyTableModel mModel;
//
//		/**
//		 * server for listening for connections
//		 **/
//		private ServerSocket mSvrSock;
//
//		/**
//		 * Creates a new <code>LoggingReceiver</code> instance.
//		 *
//		 * @param aModel model to place put received into
//		 * @param aPort  port to listen on
//		 * @throws IOException if an error occurs
//		 */
//		LoggingReceiver(MyTableModel aModel, int aPort) throws IOException {
//			setDaemon(true);
//			mModel = aModel;
//			mSvrSock = new ServerSocket(aPort);
//		}
//
//		/**
//		 * Listens for client connections
//		 **/
//		public void run() {
//			LOG.info("Thread started");
//			try {
//				while (true) {
//					LOG.debug("Waiting for a connection");
//					final Socket client = mSvrSock.accept();
//					LOG.debug("Got a connection from " + client.getInetAddress().getHostName());
//					final Thread t = new Thread(new LoggingReceiver.Slurper(client));
//					t.setDaemon(true);
//					t.start();
//				}
//			} catch (IOException e) {
//				LOG.error("Error in accepting connections, stopping.", e);
//			}
//		}
//	}
//
//	class MyTableModel extends AbstractTableModel {
//
//		/**
//		 * used to log messages
//		 **/
//		private static final Logger LOG = Logger.getLogger(MyTableModel.class);
//
//		/**
//		 * use the compare logging events
//		 **/
//		private static final Comparator MY_COMP = new Comparator() {
//			/** @see Comparator **/
//			public int compare(Object aObj1, Object aObj2) {
//				if ((aObj1 == null) && (aObj2 == null)) {
//					return 0; // treat as equal
//				} else if (aObj1 == null) {
//					return -1; // null less than everything
//				} else if (aObj2 == null) {
//					return 1; // think about it. :->
//				}
//
//				// will assume only have LoggingEvent
//				final EventDetails le1 = (EventDetails) aObj1;
//				final EventDetails le2 = (EventDetails) aObj2;
//
//				if (le1.getTimeStamp() < le2.getTimeStamp()) {
//					return 1;
//				}
//				// assume not two events are logged at exactly the same time
//				return -1;
//			}
//		};
//
//		/**
//		 * Helper that actually processes incoming events.
//		 *
//		 * @author <a href="mailto:oliver@puppycrawl.com">Oliver Burn</a>
//		 */
//		private class Processor implements Runnable {
//			/**
//			 * loops getting the events
//			 **/
//			public void run() {
//				while (true) {
//					try {
//						Thread.sleep(1000);
//					} catch (InterruptedException e) {
//						// ignore
//					}
//
//					synchronized (mLock) {
//						if (mPaused) {
//							continue;
//						}
//
//						boolean toHead = true; // were events added to head
//						boolean        needUpdate = false;
//						final Iterator it         = mPendingEvents.iterator();
//						while (it.hasNext()) {
//							final EventDetails event = (EventDetails) it.next();
//							mAllEvents.add(event);
//							toHead = toHead && (event == mAllEvents.first());
//							needUpdate = needUpdate || matchFilter(event);
//						}
//						mPendingEvents.clear();
//
//						if (needUpdate) {
//							updateFilteredEvents(toHead);
//						}
//					}
//				}
//
//			}
//		}
//
//		/**
//		 * names of the columns in the table
//		 **/
//		private static final String[] COL_NAMES = { "Time", "Priority", "Trace", "Category", "NDC", "Message" };
//
//		/**
//		 * definition of an empty list
//		 **/
//		private static final EventDetails[] EMPTY_LIST = new EventDetails[] {};
//
//		/**
//		 * used to format dates
//		 **/
//		private static final DateFormat DATE_FORMATTER = DateFormat.getDateTimeInstance(DateFormat.SHORT,
//		                                                                                DateFormat.MEDIUM);
//
//		/**
//		 * the lock to control access
//		 **/
//		private final Object                                   mLock           = new Object();
//		/**
//		 * set of all logged events - not filtered
//		 **/
//		private final SortedSet                                mAllEvents      = new TreeSet(MY_COMP);
//		/**
//		 * events that are visible after filtering
//		 **/
//		private       EventDetails[] mFilteredEvents = EMPTY_LIST;
//		/**
//		 * list of events that are buffered for processing
//		 **/
//		private final java.util.List                           mPendingEvents  = new ArrayList();
//		/**
//		 * indicates whether event collection is paused to the UI
//		 **/
//		private       boolean                                  mPaused         = false;
//
//		/**
//		 * filter for the thread
//		 **/
//		private String mThreadFilter = "";
//		/**
//		 * filter for the message
//		 **/
//		private String mMessageFilter = "";
//		/**
//		 * filter for the NDC
//		 **/
//		private String mNDCFilter = "";
//		/**
//		 * filter for the category
//		 **/
//		private String mCategoryFilter = "";
//		/**
//		 * filter for the priority
//		 **/
//		private Priority mPriorityFilter = Priority.DEBUG;
//
//		/**
//		 * Creates a new <code>MyTableModel</code> instance.
//		 */
//		MyTableModel() {
//			final Thread t = new Thread(new MyTableModel.Processor());
//			t.setDaemon(true);
//			t.start();
//		}
//
//		////////////////////////////////////////////////////////////////////////////
//		// Table Methods
//		////////////////////////////////////////////////////////////////////////////
//
//		/**
//		 * @see javax.swing.table.TableModel
//		 **/
//		public int getRowCount() {
//			synchronized (mLock) {
//				return mFilteredEvents.length;
//			}
//		}
//
//		/**
//		 * @see javax.swing.table.TableModel
//		 **/
//		public int getColumnCount() {
//			// does not need to be synchronized
//			return COL_NAMES.length;
//		}
//
//		/**
//		 * @see javax.swing.table.TableModel
//		 **/
//		public String getColumnName(int aCol) {
//			// does not need to be synchronized
//			return COL_NAMES[aCol];
//		}
//
//		/**
//		 * @see javax.swing.table.TableModel
//		 **/
//		public Class getColumnClass(int aCol) {
//			// does not need to be synchronized
//			return (aCol == 2) ? Boolean.class : Object.class;
//		}
//
//		/**
//		 * @see javax.swing.table.TableModel
//		 **/
//		public Object getValueAt(int aRow, int aCol) {
//			synchronized (mLock) {
//				final EventDetails event = mFilteredEvents[aRow];
//
//				if (aCol == 0) {
//					return DATE_FORMATTER.format(new Date(event.getTimeStamp()));
//				} else if (aCol == 1) {
//					return event.getPriority();
//				} else if (aCol == 2) {
//					return (event.getThrowableStrRep() == null) ? Boolean.FALSE : Boolean.TRUE;
//				} else if (aCol == 3) {
//					return event.getCategoryName();
//				} else if (aCol == 4) {
//					return event.getNDC();
//				}
//				return event.getMessage();
//			}
//		}
//
//		////////////////////////////////////////////////////////////////////////////
//		// Public Methods
//		////////////////////////////////////////////////////////////////////////////
//
//		/**
//		 * Sets the priority to filter events on. Only events of equal or higher property are now displayed.
//		 *
//		 * @param aPriority the priority to filter on
//		 */
//		public void setPriorityFilter(Priority aPriority) {
//			synchronized (mLock) {
//				mPriorityFilter = aPriority;
//				updateFilteredEvents(false);
//			}
//		}
//
//		/**
//		 * Set the filter for the thread field.
//		 *
//		 * @param aStr the string to match
//		 */
//		public void setThreadFilter(String aStr) {
//			synchronized (mLock) {
//				mThreadFilter = aStr.trim();
//				updateFilteredEvents(false);
//			}
//		}
//
//		/**
//		 * Set the filter for the message field.
//		 *
//		 * @param aStr the string to match
//		 */
//		public void setMessageFilter(String aStr) {
//			synchronized (mLock) {
//				mMessageFilter = aStr.trim();
//				updateFilteredEvents(false);
//			}
//		}
//
//		/**
//		 * Set the filter for the NDC field.
//		 *
//		 * @param aStr the string to match
//		 */
//		public void setNDCFilter(String aStr) {
//			synchronized (mLock) {
//				mNDCFilter = aStr.trim();
//				updateFilteredEvents(false);
//			}
//		}
//
//		/**
//		 * Set the filter for the category field.
//		 *
//		 * @param aStr the string to match
//		 */
//		public void setCategoryFilter(String aStr) {
//			synchronized (mLock) {
//				mCategoryFilter = aStr.trim();
//				updateFilteredEvents(false);
//			}
//		}
//
//		/**
//		 * Add an event to the list.
//		 *
//		 * @param aEvent a <code>EventDetails</code> value
//		 */
//		public void addEvent(EventDetails aEvent) {
//			synchronized (mLock) {
//				mPendingEvents.add(aEvent);
//			}
//		}
//
//		/**
//		 * Clear the list of all events.
//		 */
//		public void clear() {
//			synchronized (mLock) {
//				mAllEvents.clear();
//				mFilteredEvents = new EventDetails[0];
//				mPendingEvents.clear();
//				fireTableDataChanged();
//			}
//		}
//
//		/**
//		 * Toggle whether collecting events
//		 **/
//		public void toggle() {
//			synchronized (mLock) {
//				mPaused = !mPaused;
//			}
//		}
//
//		/**
//		 * @return whether currently paused collecting events
//		 **/
//		public boolean isPaused() {
//			synchronized (mLock) {
//				return mPaused;
//			}
//		}
//
//		/**
//		 * Get the throwable information at a specified row in the filtered events.
//		 *
//		 * @param aRow the row index of the event
//		 * @return the throwable information
//		 */
//		public EventDetails getEventDetails(int aRow) {
//			synchronized (mLock) {
//				return mFilteredEvents[aRow];
//			}
//		}
//
//		////////////////////////////////////////////////////////////////////////////
//		// Private methods
//		////////////////////////////////////////////////////////////////////////////
//
//		/**
//		 * Update the filtered events data structure.
//		 *
//		 * @param aInsertedToFront indicates whether events were added to front of the events. If true, then the current
//		 *                         first event must still exist in the list after the filter is applied.
//		 */
//		private void updateFilteredEvents(boolean aInsertedToFront) {
//			final long start    = System.currentTimeMillis();
//			final List filtered = new ArrayList();
//			final int  size     = mAllEvents.size();
//			final Iterator it = mAllEvents.iterator();
//
//			while (it.hasNext()) {
//				final EventDetails event = (EventDetails) it.next();
//				if (matchFilter(event)) {
//					filtered.add(event);
//				}
//			}
//
//			final EventDetails lastFirst = (mFilteredEvents.length == 0) ? null : mFilteredEvents[0];
//			mFilteredEvents = (EventDetails[]) filtered.toArray(EMPTY_LIST);
//
//			if (aInsertedToFront && (lastFirst != null)) {
//				final int index = filtered.indexOf(lastFirst);
//				if (index < 1) {
//					LOG.warn("In strange state");
//					fireTableDataChanged();
//				} else {
//					fireTableRowsInserted(0, index - 1);
//				}
//			} else {
//				fireTableDataChanged();
//			}
//
//			final long end = System.currentTimeMillis();
//			LOG.debug("Total time [ms]: " + (end - start) + " in update, size: " + size);
//		}
//
//		/**
//		 * Returns whether an event matches the filters.
//		 *
//		 * @param aEvent the event to check for a match
//		 * @return whether the event matches
//		 */
//		private boolean matchFilter(EventDetails aEvent) {
//			if (aEvent.getPriority().isGreaterOrEqual(mPriorityFilter) && (aEvent.getThreadName().indexOf(mThreadFilter)
//					>= 0) && (aEvent.getCategoryName().indexOf(mCategoryFilter) >= 0) && ((mNDCFilter.length() == 0) || (
//					(aEvent.getNDC() != null) && (aEvent.getNDC().indexOf(mNDCFilter) >= 0)))) {
//				final String rm = aEvent.getMessage();
//				if (rm == null) {
//					// only match if we have not filtering in place
//					return (mMessageFilter.length() == 0);
//				} else {
//					return (rm.indexOf(mMessageFilter) >= 0);
//				}
//			}
//
//			return false; // by default not match
//		}
//	}
//
//	class XMLFileHandler extends DefaultHandler {
//		/**
//		 * represents the event tag
//		 **/
//		private static final String TAG_EVENT = "log4j:event";
//		/**
//		 * represents the message tag
//		 **/
//		private static final String TAG_MESSAGE = "log4j:message";
//		/**
//		 * represents the ndc tag
//		 **/
//		private static final String TAG_NDC = "log4j:NDC";
//		/**
//		 * represents the throwable tag
//		 **/
//		private static final String TAG_THROWABLE = "log4j:throwable";
//		/**
//		 * represents the location info tag
//		 **/
//		private static final String TAG_LOCATION_INFO = "log4j:locationInfo";
//
//		/**
//		 * where to put the events
//		 **/
//		private final MyTableModel mModel;
//		/**
//		 * the number of events in the document
//		 **/
//		private       int                                    mNumEvents;
//
//		/**
//		 * the time of the event
//		 **/
//		private long mTimeStamp;
//		/**
//		 * the priority (level) of the event
//		 **/
//		private Level mLevel;
//		/**
//		 * the category of the event
//		 **/
//		private String mCategoryName;
//		/**
//		 * the NDC for the event
//		 **/
//		private String mNDC;
//		/**
//		 * the thread for the event
//		 **/
//		private String mThreadName;
//		/**
//		 * the msg for the event
//		 **/
//		private String mMessage;
//		/**
//		 * the throwable details the event
//		 **/
//		private String[] mThrowableStrRep;
//		/**
//		 * the location details for the event
//		 **/
//		private String mLocationDetails;
//		/**
//		 * buffer for collecting text
//		 **/
//		private final StringBuffer mBuf = new StringBuffer();
//
//		/**
//		 * Creates a new <code>XMLFileHandler</code> instance.
//		 *
//		 * @param aModel where to add the events
//		 */
//		XMLFileHandler(MyTableModel aModel) {
//			mModel = aModel;
//		}
//
//		/**
//		 * @see DefaultHandler
//		 **/
//		public void startDocument() throws SAXException {
//			mNumEvents = 0;
//		}
//
//		/**
//		 * @see DefaultHandler
//		 **/
//		public void characters(char[] aChars, int aStart, int aLength) {
//			mBuf.append(String.valueOf(aChars, aStart, aLength));
//		}
//
//		/**
//		 * @see DefaultHandler
//		 **/
//		public void endElement(String aNamespaceURI, String aLocalName, String aQName) {
//			if (TAG_EVENT.equals(aQName)) {
//				addEvent();
//				resetData();
//			} else if (TAG_NDC.equals(aQName)) {
//				mNDC = mBuf.toString();
//			} else if (TAG_MESSAGE.equals(aQName)) {
//				mMessage = mBuf.toString();
//			} else if (TAG_THROWABLE.equals(aQName)) {
//				final StringTokenizer st = new StringTokenizer(mBuf.toString(), "\n\t");
//				mThrowableStrRep = new String[st.countTokens()];
//				if (mThrowableStrRep.length > 0) {
//					mThrowableStrRep[0] = st.nextToken();
//					for (int i = 1; i < mThrowableStrRep.length; i++) {
//						mThrowableStrRep[i] = "\t" + st.nextToken();
//					}
//				}
//			}
//		}
//
//		/**
//		 * @see DefaultHandler
//		 **/
//		public void startElement(String aNamespaceURI, String aLocalName, String aQName, Attributes aAtts) {
//			mBuf.setLength(0);
//
//			if (TAG_EVENT.equals(aQName)) {
//				mThreadName = aAtts.getValue("thread");
//				mTimeStamp = Long.parseLong(aAtts.getValue("timestamp"));
//				mCategoryName = aAtts.getValue("logger");
//				mLevel = Level.toLevel(aAtts.getValue("level"));
//			} else if (TAG_LOCATION_INFO.equals(aQName)) {
//				mLocationDetails =
//						aAtts.getValue("class") + "." + aAtts.getValue("method") + "(" + aAtts.getValue("file") + ":"
//								+ aAtts.getValue("line") + ")";
//			}
//		}
//
//		/**
//		 * @return the number of events in the document
//		 **/
//		int getNumEvents() {
//			return mNumEvents;
//		}
//
//		////////////////////////////////////////////////////////////////////////////
//		// Private methods
//		////////////////////////////////////////////////////////////////////////////
//
//		/**
//		 * Add an event to the model
//		 **/
//		private void addEvent() {
//			mModel.addEvent(
//					new EventDetails(mTimeStamp, mLevel, mCategoryName, mNDC, mThreadName, mMessage, mThrowableStrRep,
//					                                           mLocationDetails));
//			mNumEvents++;
//		}
//
//		/**
//		 * Reset the data for an event
//		 **/
//		private void resetData() {
//			mTimeStamp = 0;
//			mLevel = null;
//			mCategoryName = null;
//			mNDC = null;
//			mThreadName = null;
//			mMessage = null;
//			mThrowableStrRep = null;
//			mLocationDetails = null;
//		}
//	}
//}
