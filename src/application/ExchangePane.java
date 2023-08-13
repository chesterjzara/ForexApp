package application;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class ExchangePane extends Pane {
	public int posX;
	public int posY;
	
	public ExchangePane(int x, int y) {
		posX = x;
		posY = y;
		this.addEventHandler(MouseEvent.MOUSE_CLICKED, paneEventHandler());
	}
	
	public EventHandler<MouseEvent> paneEventHandler(){
        return event -> {
            //Node node = (Node) event.getTarget();
            System.out.println(posX + " " + posY);
        };
    }
}
