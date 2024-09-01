package is.shapes.controller;

import is.command.CommandHandler;
import is.shapes.model.GraphicObject;
import is.shapes.specificcommand.MoveCommand;
import is.shapes.specificcommand.ZoomCommand;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.io.Serial;

import javax.swing.*;

public class GraphicObjectController extends JPanel {
	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = 9177631848186263965L;

	private final CommandHandler cmdHandler;

	private GraphicObject subject;

	private final JPanel grid;

	private final JPanel zoom;

	static final int offset = 10;

	static final double zoom_factor = 0.1;

	public void setControlledObject(GraphicObject go) {
		subject = go;
	}

	public GraphicObjectController(CommandHandler cmdH) {
		this(null, cmdH);
	}

	public GraphicObjectController(GraphicObject go, CommandHandler cmdH) {
		cmdHandler = cmdH;
		subject = go;

		grid = new JPanel(new GridLayout(3, 3));
		zoom = new JPanel(new GridLayout(1, 2));

		JButton minus = new JButton("-");

		minus.addActionListener(e -> {
			if (subject != null) {
				// subject.scale(1.0-zoom_factor);
				cmdHandler.handle(new ZoomCommand(subject, 1.0 - zoom_factor));
			}
		});

		zoom.add(minus);

		JButton plus = new JButton("+");
		plus.addActionListener(e -> {
			if (subject != null) {
				// subject.scale(1.0 + zoom_factor);
				cmdHandler.handle(new ZoomCommand(subject, 1.0 + zoom_factor));
			}
		});

		zoom.add(plus);

		zoom.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		JButton nw = new JButton("\\");

		nw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (subject == null)
					return;
				Point2D p = subject.getPosition();
				// subject.moveTo(p.getX() - offset, p.getY() - offset);
				cmdHandler.handle(new MoveCommand(subject, new Point2D.Double(p.getX() - offset, p.getY() - offset)));
			}
		});
		grid.add(nw);

		JButton n = new JButton("|");

		n.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (subject == null)
					return;
				Point2D p = subject.getPosition();
				// subject.moveTo(p.getX(), p.getY() - offset);
				cmdHandler.handle(new MoveCommand(subject, new Point2D.Double(p.getX(), p.getY() - offset)));
			}
		});

		grid.add(n);

		JButton ne = new JButton("/");
		ne.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (subject == null)
					return;
				Point2D p = subject.getPosition();
				// subject.moveTo(p.getX() + offset, p.getY() - offset);
				cmdHandler.handle(new MoveCommand(subject, new Point2D.Double(p.getX() + offset, p.getY() - offset)));

			}
		});

		grid.add(ne);

		JButton w = new JButton("<-");
		w.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (subject == null)
					return;
				Point2D p = subject.getPosition();
				// subject.moveTo(p.getX() - offset, p.getY());
				cmdHandler.handle(new MoveCommand(subject, new Point2D.Double(p.getX() - offset, p.getY())));
			}
		});

		grid.add(w);

		grid.add(zoom);

		JButton e = new JButton("->");
		e.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (subject == null)
					return;
				Point2D p = subject.getPosition();
				// subject.moveTo(p.getX() + offset, p.getY());
				cmdHandler.handle(new MoveCommand(subject, new Point2D.Double(p.getX() + offset, p.getY())));
			}
		});

		grid.add(e);

		JButton sw = new JButton("/");

		sw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (subject == null)
					return;
				Point2D p = subject.getPosition();
				// subject.moveTo(p.getX() - offset, p.getY() + offset);
				cmdHandler.handle(new MoveCommand(subject, new Point2D.Double(p.getX() - offset, p.getY() + offset)));
			}
		});
		grid.add(sw);

		JButton s = new JButton("|");
		s.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (subject == null)
					return;
				Point2D p = subject.getPosition();
				// subject.moveTo(p.getX(), p.getY() + offset);
				cmdHandler.handle(new MoveCommand(subject, new Point2D.Double(p.getX(), p.getY() + offset)));
			}
		});

		grid.add(s);

		JButton se = new JButton("\\");
		se.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (subject == null)
					return;
				Point2D p = subject.getPosition();
				// subject.moveTo(p.getX() + offset, p.getY() + offset);
				cmdHandler.handle(new MoveCommand(subject, new Point2D.Double(p.getX() + offset, p.getY() + offset)));
			}
		});
		grid.add(se);
		add(grid);
		setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
	}

}
