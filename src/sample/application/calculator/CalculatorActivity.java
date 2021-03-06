package sample.application.calculator;

import java.math.BigDecimal;

import java.text.DecimalFormat;

import android.os.Bundle;
import android.app.Activity;
import android.text.ClipboardManager;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CalculatorActivity extends Activity {

	public String strTemp = "";
	public String strResult = "0";
	public Integer operator = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        readPreferences();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_calculator, menu);
        return true;
    }
    
    
    
    public void numKeyOnClick(View v){
   
    	String strInKey = (String) ((Button)v).getText();
    	
    	if(strInKey.equals(".")){
    		if(this.strTemp.length() == 0){
    			this.strTemp = "0.";
    		}else{
    			if(this.strTemp.indexOf(".") == -1){
    				this.strTemp = this.strTemp + ".";
    			}
    		}
    	}else{
    		this.strTemp = this.strTemp + strInKey;
    	}
    	
    	this.showNumber(this.strTemp);
    }
    
    private void showNumber(String strNum){
  
    	DecimalFormat form = new DecimalFormat("#,##0");
    	String strDecimal = "";
    	String strInt = "";
    	String fText = "";
    	
    	if(strNum.length() > 0){
    		int decimalPoint = strNum.indexOf(".");				
    		if(decimalPoint > -1){
    			strDecimal = strNum.substring(decimalPoint);
    			strInt = strNum.substring(0,decimalPoint);
    		}else{
    			strInt = strNum;
    		}
    		
    		fText = form.format(Double.parseDouble(strInt)) + strDecimal;
    	}else{
    		fText = "0";
    	}
    	
    	((TextView)this.findViewById(R.id.displayPanel)).setText(fText);
    }
    
    public void fuctionKeyOnClick(View v){
    	switch(v.getId()){
    	case R.id.keypadAC:
    		strTemp = "";
    		strResult = "0";
    		operator = 0;
    		break;
    	case R.id.keypadC:
    		strTemp = "";
    		break;
    	case R.id.keypadBS:
    		if(strTemp.length() == 0)return;
    		else strTemp = strTemp.substring(0,strTemp.length()-1);
    		break;
    	case R.id.keypadCopy:
    		ClipboardManager cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
    		cm.setText(((TextView)findViewById(R.id.displayPanel)).getText());
    		return;
    	}
    	showNumber(this.strTemp);
    }
   
    
    public void operatorKeyOnClick(View v){
     
    	if(this.operator != 0){
    		if(this.strTemp.length() > 0){
    			this.strResult = doCalc();
    			this.showNumber(this.strResult);
    		}
    	}else{
    		if(this.strTemp.length() > 0){
    			this.strResult = this.strTemp;
    		}
    	}
    	
    	this.strTemp="";
    	
    	if(v.getId() == R.id.keypadEq){
    		this.operator = 0;
    	}else{
    		this.operator = v.getId();
    	}
    }
    
    private String doCalc(){
    	BigDecimal bd1 = new BigDecimal(strResult);
    	BigDecimal bd2 = new BigDecimal(strTemp);
    	BigDecimal result = BigDecimal.ZERO;
    	
    	switch(operator){
    	case R.id.keypadAdd:
    		result = bd1.add(bd2);
    		break;
    	case R.id.keypadSub:
    		result = bd1.subtract(bd2);
    		break;
    	case R.id.keypadMulti:
    		result = bd1.multiply(bd2);
    		break;
    	case R.id.keypadDiv:
    		if(!bd2.equals(BigDecimal.ZERO)){
    			result = bd1.divide(bd2,12,3);
    		}else{
    			Toast toast = Toast.makeText(this, R.string.toast_div_by_zero, 1000);
    			toast.show();
    		}
    		break;
    	}
    	
    	if(result.toString().indexOf(".") >= 0){
    		return result.toString().replaceAll("¥¥.0+$|0+$", "");
    	}else{
    		return result.toString();
    	}
    }
    
    void writePreferences(){
    	SharedPreferences prefs = getSharedPreferences("CalcPrefs", MODE_PRIVATE);
    	SharedPreferences.Editor editor = prefs.edit();
    	editor.putString("strTemp",  strTemp);
    	editor.putString("strResult", strResult);
    	editor.putInt("opetator", operator);
    	editor.putString("strDisplay", ((TextView)findViewById(R.id.displayPanel)).getText().toString());
    	editor.commit();
    }
    
    void readPreferences(){
    	SharedPreferences prefs = getSharedPreferences("CalcPrefs", MODE_PRIVATE);
    	strTemp = prefs.getString("strTemp", "");
    	strResult = prefs.getString("strResult", "0");
    	operator = prefs.getInt("operator", 0);
    	((TextView)findViewById(R.id.displayPanel)).setText(prefs.getString("strDisplay", "0"));
    }

	@Override
	protected void onStop() {
		writePreferences();
		super.onStop();
	}
}