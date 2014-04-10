package ws.fiae.android.pwgen;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class MainActivity extends Activity
{
	
	private static final String DEFAULT_LETTERS = "abcdefghijklmnopqrstuvwxyz";
	private static final String DEFAULT_LETTERS_UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String DEFAULT_INTEGERS = "0123456789";
	private static final String DEFAULT_EASY_A = "bcdfghjklmnpqrstvwxyz";
	private static final String DEFAULT_EASY_B = "aeiou";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		// Set Spinner Data
		Spinner spnr = (Spinner)this.findViewById(R.id.length_spinner);
		String[] spnrdata = new String[100];
		for(int i=0; i<100; i++) {
			spnrdata[i] = Integer.toString((i+1));
		}
		
		ArrayAdapter<String> adr = new ArrayAdapter<String>(this, 
			android.R.layout.simple_spinner_item, spnrdata);
		
		spnr.setAdapter(adr);
		spnr.setSelection(11);
		
		this.selfDestruct(null);
		
    }
	
	public void selfDestruct(View view) {
		RadioGroup type = (RadioGroup) findViewById(R.id.radiogroup);
		int selected_type = type.getCheckedRadioButtonId();
		
		String chars="";
		switch(selected_type) {
				
			case R.id.type_integers:
				chars = DEFAULT_INTEGERS;
				break;
				
			case R.id.type_letters_both:
				chars = DEFAULT_LETTERS+DEFAULT_LETTERS_UPPERCASE;
				break;
				
			case R.id.type_letters_lower:
				chars = DEFAULT_LETTERS;
				break;
				
			case R.id.type_letters_upper:
				chars = DEFAULT_LETTERS_UPPERCASE;
				break;
				
			case R.id.type_both_multicase:
				chars = DEFAULT_INTEGERS+DEFAULT_LETTERS+DEFAULT_LETTERS_UPPERCASE;
				break;
				
			case R.id.type_custom:
				EditText custom_text = (EditText) this.findViewById(R.id.text_custom);
				chars = custom_text.getText().toString();
				break;
				
			default:
				chars = DEFAULT_INTEGERS+DEFAULT_LETTERS;
				break;
				
		}
		
		Spinner spnr = (Spinner)this.findViewById(R.id.length_spinner);
		int max = Integer.parseInt(spnr.getSelectedItem().toString());
		
		String password = "";
		if(selected_type==R.id.type_easy) {
			password = this.getEasyPassword(max);
		} else {
			password = this.getRandomPassword(chars, max);
		}
		
		EditText result = (EditText)this.findViewById(R.id.result);
		result.setText(password);
		
	}
	
	
	private String getRandomSubstring(String str) {
		int integer = (int)(Math.random()*str.length());
		return str.substring(integer, integer+1);
	}
	
	
	private String getRandomPassword(String chars, int length) {
		String password = "";
		for(int i=0; i<length; i++) {
			password += this.getRandomSubstring(chars);
		}
		return password;
	}
	
	
	private String getEasyPair() {
		return this.getRandomSubstring(DEFAULT_EASY_A)+
		    this.getRandomSubstring(DEFAULT_EASY_B);
	}
	
	
	private String getEasyPassword(int length) {
		int newlength = length;
		if(length%3>0) {
			newlength = ((int)newlength/3)*3;
		}
		
		String pairs = "";
		String integers = "";
		
		while( (pairs.length()+integers.length())<newlength ) {
			pairs += this.getEasyPair();
			integers += this.getRandomSubstring(DEFAULT_INTEGERS);
		}
		
		while( (pairs.length()+integers.length())<length ) {
			integers += this.getRandomSubstring(DEFAULT_INTEGERS);
		}
		
		return pairs+integers;
	}
	
	
	
	// Options Menu
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inf = this.getMenuInflater();
		inf.inflate(R.menu.menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem menu) {
		this.onAboutClicked();
		return true;
	}
	
	public void onAboutClicked() {
		AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this); 
		dlgAlert.setMessage("By Christian Blechert (http://fiae.ws)");
		dlgAlert.setTitle("About"); 
		dlgAlert.setPositiveButton("OK", null); 
		dlgAlert.setCancelable(true); 
		dlgAlert.setIcon(R.drawable.appicon);
		
		AlertDialog dlg = dlgAlert.create();
		dlg.setTitle("About");
		dlg.show();
		
	}
	
	
	
}
