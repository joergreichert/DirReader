package dirreader;

public class DRMain {
	
	public DRMain() {
		DRView view = new DRView();
		DREvents events = new DREvents(view);
		view.start();
	}

	public static void main(String [] args) {
		System.setProperty("java.library.path", System.getProperty("java.class.path"));
		new DRMain();
	}
}
