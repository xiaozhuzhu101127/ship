package com.topjet.shipper.task;

import java.io.File;

import com.topjet.shipper.util.Common;

public class ReadContactTask 
implements Runnable{
	public interface IReadListener {
		void readStart();
		void readFinish(String names,String number);
		void readFailed();
	}
	StringBuilder names ,  numbers;	
	IReadListener readListener;
	public ReadContactTask(IReadListener readListener){
		this.readListener = readListener;
		names = new StringBuilder();
		numbers  = new StringBuilder();
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
		readListener.readStart();
		Common.readContacts(names, numbers);	 
		readListener.readFinish(names.toString(),numbers.toString());
		}
		catch(Exception ex){
			readListener.readFailed();
		}
	}

}
