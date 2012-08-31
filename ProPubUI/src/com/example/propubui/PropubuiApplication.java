package com.example.propubui;

import propub.Gui;

import com.vaadin.Application;
import com.vaadin.ui.*;

public class PropubuiApplication extends Application {
	@Override
	public void init() {
		Window mainWindow = new Window("Propubui Application");
		Label label = new Label("Hello Vaadin user");
		mainWindow.setContent(new Gui());
//		mainWindow.addComponent(new Gui());
		setMainWindow(mainWindow);
	}

}
