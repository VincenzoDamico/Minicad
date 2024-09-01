package is;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.StringReader;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import is.command.HistoryCommandHandler;
import is.interpreter.Parser.Parser;
import is.shapes.controller.GraphicObjectController;
import is.shapes.model.CircleObject;
import is.shapes.model.ImageObject;
import is.shapes.model.RectangleObject;
import is.shapes.view.*;
import is.utility.MyConstants;

public class Main {

	public static void main(String[] args) {

		JFrame f = new JFrame();

		JToolBar toolbar = new JToolBar();

		JButton undoButt = new JButton("Undo");
		JButton redoButt = new JButton("Redo");
		final GraphicObjectPanel gpanel = new GraphicObjectPanel();

		GraphicObjectViewFactory.FACTORY.installView(RectangleObject.class, new RectangleObjectView());
		GraphicObjectViewFactory.FACTORY.installView(CircleObject.class, new CircleObjectView());
		GraphicObjectViewFactory.FACTORY.installView(ImageObject.class, new ImageObjectView());
		HistoryCommandHandler handler = new HistoryCommandHandler();
		undoButt.addActionListener(evt -> handler.undo());
		redoButt.addActionListener(evt -> handler.redo());
		toolbar.add(undoButt);
		toolbar.add(redoButt);
		gpanel.setPreferredSize(new Dimension(400, 400));
		final GraphicObjectController goc = new GraphicObjectController(handler);
		gpanel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				goc.setControlledObject(gpanel.getGraphicObjectAt(e.getPoint()));
			}
		});
		f.add(toolbar, BorderLayout.NORTH);
		f.add(new JScrollPane(gpanel), BorderLayout.CENTER);
		JPanel controlPanel = new JPanel(new FlowLayout());
		controlPanel.add(goc);
		f.setTitle(MyConstants.TITLE);
		f.getContentPane().add(controlPanel, BorderLayout.SOUTH);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.pack();
		f.setVisible(true);
		System.out.println(MyConstants.OPEN);
		Scanner sc = new Scanner(System.in);
		StringReader sr = new StringReader("");
		Parser p=new Parser(gpanel,sr,handler);
		while (true) {
			String comando = sc.nextLine();
			if ("exit".equalsIgnoreCase(comando.trim())) {
				System.exit(1);
			}
			try {
				if (!comando.trim().isEmpty()) {
					sr = new StringReader(comando);
					p.setReader(sr);
				}
			} catch (RuntimeException ex){
				String s = ex.toString().replace("is.utility.SyntaxException: ", "").replace("java.lang.IllegalArgumentException: ", "");
				System.err.println("\n"+s);
			}
        }


	}
}