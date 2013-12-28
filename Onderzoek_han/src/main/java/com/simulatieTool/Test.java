package com.simulatieTool;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.SimpleHtmlSerializer;
import org.htmlcleaner.TagNode;

public class Test {

	public static File homePrototypesDir;
	public static org.htmlcleaner.HtmlCleaner cleaner = new org.htmlcleaner.HtmlCleaner();
	static final CleanerProperties props = new CleanerProperties();
	static final SimpleHtmlSerializer htmlSerializer = new SimpleHtmlSerializer(
			props);

	public static void main(String[] args) throws IOException {

		File prototypesDir = new File("C:/Users/ndizigiye/Downloads/Prototype1");
		System.out.println(Test.class.getResource("/interfaces").getPath());
		System.out.println(prototypesDir.getName());
		try {
			homePrototypesDir = new File(Test.class.getResource("/interfaces")
					.getPath() + "/" + prototypesDir.getName());
			FileUtils.cleanDirectory(homePrototypesDir);
		}

		catch (NullPointerException e) {
			homePrototypesDir.mkdir();
		}

		 try {
		 FileUtils.copyDirectory(prototypesDir, homePrototypesDir);
		 } catch (IOException e) {
		 // TODO Auto-generated catch block
		 e.printStackTrace();
		 }
		 System.out.println("copyed to: " + homePrototypesDir.getPath());

		File screenDir = new File(homePrototypesDir.getPath()
				+ "/review/screens");
		Collection<File> screens = FileUtils.listFiles(screenDir,
				TrueFileFilter.TRUE, TrueFileFilter.TRUE);

		ArrayList<File> screenList = new ArrayList<File>(screens);

		for (File screen : screenList) {
			TagNode node = cleaner.clean(screen);
			String html = cleaner.getInnerHtml(node);
			System.out.println("----------------");
			TagNode screenNode = node.findElementByAttValue("class",
					"screen firer ie-background commentable", true, true);
			String screenName = screenNode.getAttributeByName("name");
			File newDest = new File(homePrototypesDir.getPath()
					+ "/review/screens/" + screenName + ".html");

			if (!newDest.exists()) {
				FileUtils.write(newDest, html, Charset.defaultCharset());
				FileUtils.forceDelete(screen);
			}
		}

	}
}
