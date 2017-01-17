package downloadSoft;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

public class MycomboBox extends AbstractListModel<String> implements ComboBoxModel<String> {
	
	private static final long serialVersionUID = 516716573213546L;
	String selecteditem = null;
	String[] test;
	
	public MycomboBox(String[] test){
		this.test = test;
	}
	
	public String getElementAt(int index) {
		return test[index];
	}
	
	public int getSize() {
		return test.length;
	}
	
	public void setSelectedItem(Object item) {
		selecteditem = (String) item;
	}
	
	public Object getSelectedItem() {
		return selecteditem;
	}
	
	public int getIndex() {
		for (int i = 0; i < test.length; i++) {
			if (test[i].equals(getSelectedItem()))
				return i;
			//break;
		}
		return 0;
	}
}

