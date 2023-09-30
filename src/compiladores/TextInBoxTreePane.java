
package compiladores;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;

import org.abego.treelayout.TreeForTreeLayout;
import org.abego.treelayout.TreeLayout;
public class TextInBoxTreePane extends JComponent {
	private final TreeLayout<TextInBox> treeLayout;
	private boolean boxVisible = true;

	private TreeForTreeLayout<TextInBox> getTree() {
		return treeLayout.getTree();
	}

	private Iterable<TextInBox> getChildren(TextInBox parent) {
		return getTree().getChildren(parent);
	}

	private Rectangle2D.Double getBoundsOfNode(TextInBox node) {
		return treeLayout.getNodeBounds().get(node);
	}

	/**
	 * Specifies the tree to be displayed by passing in a {@link TreeLayout} for
	 * that tree.
	 * 
	 * @param treeLayout the {@link TreeLayout} to be displayed
	 */
	public TextInBoxTreePane(TreeLayout<TextInBox> treeLayout) {
		this.treeLayout = treeLayout;

		Dimension size = treeLayout.getBounds().getBounds().getSize();
		setPreferredSize(size);
	}

	public boolean isBoxVisible() {
		return boxVisible;
	}

	public void setBoxVisible(boolean boxVisible) {
		this.boxVisible = boxVisible;
	}

	// -------------------------------------------------------------------
	// painting

	private final static int ARC_SIZE = 10;
	private final static Color BOX_COLOR = Color.white;
	private final static Color BORDER_COLOR = new Color(37,150,190);
	private final static Color TEXT_COLOR = Color.black;

	private void paintEdges(Graphics g, TextInBox parent) {
		if (!getTree().isLeaf(parent)) {
			Rectangle2D.Double b1 = getBoundsOfNode(parent);
			double x1 = b1.getCenterX();
			double y1 = b1.getCenterY();
			for (TextInBox child : getChildren(parent)) {
				Rectangle2D.Double b2 = getBoundsOfNode(child);
				g.drawLine((int) x1, (int) y1, (int) b2.getCenterX(),
						(int) b2.getCenterY());

				paintEdges(g, child);
			}
		}
	}

	private void paintBox(Graphics g, TextInBox textInBox) {
		Rectangle2D.Double box = getBoundsOfNode(textInBox);
		if(isBoxVisible()) {
			// draw the box in the background
			g.setColor(BOX_COLOR);
			g.fillRoundRect((int) box.x, (int) box.y, (int) box.width - 1,
					(int) box.height - 1, ARC_SIZE, ARC_SIZE);
			g.setColor(BORDER_COLOR);
			g.drawRoundRect((int) box.x, (int) box.y, (int) box.width - 1,
					(int) box.height - 1, ARC_SIZE, ARC_SIZE);
		} else {
			g.setColor(getBackground());
			g.fillRect((int) box.x, (int) box.y, (int) box.width - 1,(int) box.height - 1);
		}

		// draw the text on top of the box (possibly multiple lines)
		g.setColor(TEXT_COLOR);
		String[] lines = textInBox.text.split("\n");
		FontMetrics m = getFontMetrics(getFont());
		int centerX = (int) box.getCenterX();
		int y = (int) box.y + m.getAscent() + m.getLeading() + 1;
		for (String line : lines) {
			Rectangle2D r = m.getStringBounds(line, g);
			int x = centerX - (int) (r.getWidth() / 2);
			g.drawString(line, x, y);
			y += m.getHeight();
		}
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		paintEdges(g, getTree().getRoot());

		// paint the boxes
		for (TextInBox textInBox : treeLayout.getNodeBounds().keySet()) {
			paintBox(g, textInBox);
		}
	}
}