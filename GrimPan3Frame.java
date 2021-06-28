package hufs.cse.grimpan3;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.Shape;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JColorChooser;
import javax.swing.JRadioButton;

public class GrimPan3Frame extends JFrame {

	private GrimPanModel model = null;
	
	private JPanel contentPane;
	private JMenuBar menuBar;
	private JMenu menuFile;
	private JMenu menuHelp;
	private JMenuItem jmiNew;
	private JMenuItem jmiOpen;
	private JMenuItem jmiExit;
	private JMenuItem jmiAbout;

	GrimPan3Frame thisClass =  this;
	private DrawPanel drawPanel;
	private JMenu menuShape;
	private JMenu menuSetting;
	JRadioButtonMenuItem rdbtnmntmLine;
	JRadioButtonMenuItem rdbtnmntmPen;	
	JRadioButtonMenuItem rdbtnmntmPoly;

	private JMenuItem jmiStrokeWidth;
	private JMenuItem jmiStrokeColor;
	private JMenuItem jmiFillColor;
	private JCheckBoxMenuItem jcmiFill;

	private ButtonGroup btnGroup = new ButtonGroup();
	private JRadioButtonMenuItem rdbtnmntmReg;
	private JRadioButtonMenuItem rdbtnmntmEllipse;

	/**
	 * Create the frame.
	 */
	public GrimPan3Frame() {
		model = new GrimPanModel();
		initialize();
	}
	
	void initialize(){
		
		setTitle("그림판");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 50, 800, 600);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		menuFile = new JMenu("File  ");
		menuBar.add(menuFile);
		
		jmiNew = new JMenuItem("New  ");
		jmiNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearDrawPanel();
			}
		});
		menuFile.add(jmiNew);
		
		jmiOpen = new JMenuItem("Open ");
		menuFile.add(jmiOpen);
		
		menuFile.addSeparator();
		
		jmiExit = new JMenuItem("Exit  ");
		jmiExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		menuFile.add(jmiExit);
		
		menuShape = new JMenu("Shape  ");
		menuBar.add(menuShape);
		
		rdbtnmntmLine = new JRadioButtonMenuItem("선분");
		rdbtnmntmLine.setSelected(true);
		rdbtnmntmLine.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				model.setEditState(GrimPanModel.SHAPE_LINE);
			}
		});
		menuShape.add(rdbtnmntmLine);
		
		rdbtnmntmPen = new JRadioButtonMenuItem("연필");
		rdbtnmntmPen.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				model.setEditState(GrimPanModel.SHAPE_PENCIL);

			}
		});
		menuShape.add(rdbtnmntmPen);
		
		rdbtnmntmPoly = new JRadioButtonMenuItem("다각형");
		rdbtnmntmPoly.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				model.setEditState(GrimPanModel.SHAPE_POLYGON);
			}
		});
		menuShape.add(rdbtnmntmPoly);
		
		rdbtnmntmReg = new JRadioButtonMenuItem("정다각형");
		rdbtnmntmReg.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				model.setEditState(GrimPanModel.SHAPE_REGULAR);
				Object[] possibleValues = { 
						"3", "4", "5", "6", "7",
						"8", "9", "10", "11", "12"
						};
				Object selectedValue = JOptionPane.showInputDialog(null,
						"Choose one", "Input",
				        JOptionPane.INFORMATION_MESSAGE, null,
			            possibleValues, possibleValues[0]);
				model.setNPolygon(Integer.parseInt((String)selectedValue));
				drawPanel.repaint();
			}
		});
		menuShape.add(rdbtnmntmReg);
		
		rdbtnmntmEllipse = new JRadioButtonMenuItem("타원형");
		rdbtnmntmEllipse.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				model.setEditState(GrimPanModel.SHAPE_OVAL);
			}
		});
		menuShape.add(rdbtnmntmEllipse);
		
		menuSetting = new JMenu("Setting ");
		menuBar.add(menuSetting);
		
		jmiStrokeWidth = new JMenuItem("선두께");
		jmiStrokeWidth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String inputVal = JOptionPane.showInputDialog("선두께", "1");
				if (inputVal!=null){
					model.setShapeStroke(new BasicStroke(Integer.parseInt(inputVal)));
					drawPanel.repaint();
				}
			}
		});

		menuSetting.add(jmiStrokeWidth);
		
		jmiStrokeColor = new JMenuItem("선색깔");
		jmiStrokeColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color color = 
						JColorChooser.showDialog(thisClass, 
								"Choose a color",
								Color.black);					
					model.setStrokeColor(color);
					drawPanel.repaint();
			}
		});
		menuSetting.add(jmiStrokeColor);
		
		jcmiFill = new JCheckBoxMenuItem("채우기");
		jcmiFill.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				boolean fillState = jcmiFill.getState();
				model.setShapeFill(fillState);
				drawPanel.repaint();
			}
		});
		menuSetting.add(jcmiFill);
		
		jmiFillColor = new JMenuItem("채움색깔");
		jmiFillColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color color = 
						JColorChooser.showDialog(thisClass, 
								"Choose a color",
								Color.black);					
					model.setFillColor(color);
					drawPanel.repaint();
			}
		});
		menuSetting.add(jmiFillColor);
		
		menuHelp = new JMenu("Help  ");
		menuBar.add(menuHelp);
		
		jmiAbout = new JMenuItem("About");
		jmiAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(thisClass,
						"GrimPan Ver0.3 \nProgrammed by cskim, cse, hufs.ac.kr", 
						 "About", JOptionPane.INFORMATION_MESSAGE);

			}
		});
		menuHelp.add(jmiAbout);

		btnGroup.add(rdbtnmntmLine);
		btnGroup.add(rdbtnmntmPen);
		btnGroup.add(rdbtnmntmPoly);
		btnGroup.add(rdbtnmntmReg);
		btnGroup.add(rdbtnmntmEllipse);
		
		
		contentPane = new JPanel();
		contentPane.setBorder(null);
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		drawPanel = new DrawPanel(model);
		contentPane.add(drawPanel, BorderLayout.CENTER);
	}

	void clearDrawPanel(){
		model.shapeList = new ArrayList<GrimShape>();
		model.polygonPoints = new ArrayList<Point>();
		
		drawPanel.repaint();
	}
}
