package com.ye.FirstBoot.annotation;

@FirstBootService("FirstBootService")
public class Target {
	@DefaultValue(value = "FirstBoot_Target")
	private String name;
   
	public  void printInfo() {
         System.out.println("************** "+name);
	}
}
