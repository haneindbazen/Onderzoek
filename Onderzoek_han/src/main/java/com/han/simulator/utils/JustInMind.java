package com.han.simulator.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.SimpleHtmlSerializer;
import org.htmlcleaner.TagNode;

/**
 * This class handles generated Htmls from JustinMind
 * 
 * @author Armand Ndizigiye
 * @version 0.1
 */
public class JustInMind {

	public static File homePrototypesDir;
	public static org.htmlcleaner.HtmlCleaner cleaner = new org.htmlcleaner.HtmlCleaner();
	static final CleanerProperties props = new CleanerProperties();
	static final SimpleHtmlSerializer htmlSerializer = new SimpleHtmlSerializer(
			props);

	/**
	 * Copy the prototype dir to the project path
	 * 
	 * @param String
	 *            path - The path to the prototype directory
	 */
	public static void CopyPrototype(String path) {
		File prototypesDir = new File(path);
		System.out.println("---" + prototypesDir.getName());

		homePrototypesDir = new File(JustInMind.class.getResource(Workspace.AppDir.getPath()
				+"/interfaces").getPath()
				+ "/" + prototypesDir.getName());
		if (!homePrototypesDir.exists()) {
			homePrototypesDir.mkdir();
		}
		try {
			FileUtils.cleanDirectory(homePrototypesDir);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			FileUtils.copyDirectory(prototypesDir, homePrototypesDir);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Rename the interfaces
	 */
	public static void RenameInterfaces() {
		File screenDir = new File(homePrototypesDir.getPath()
				+ "/review/screens");
		Collection<File> screens = FileUtils.listFiles(screenDir,
				TrueFileFilter.TRUE, TrueFileFilter.TRUE);

		ArrayList<File> screenList = new ArrayList<File>(screens);

		for (File screen : screenList) {
			TagNode node = null;
			try {
				node = cleaner.clean(screen);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			TagNode[] cssLinks = node.getElementsByAttValue("rel",
					"stylesheet", true, true);

			for (TagNode cssLink : cssLinks) {
				String link = cssLink.getAttributeByName("href");
				link = link.replace("./", "/simulator/interfaces/Prototype1/");
				cssLink.setAttribute("href", link);
			}

			TagNode alignmentBox = node.findElementByAttValue("id",
					"alignmentBox", true, true);
			alignmentBox.removeFromTree();
			String html = cleaner.getInnerHtml(node);
			System.out.println("----------------");
			TagNode screenNode = node.findElementByAttValue("class",
					"screen firer ie-background commentable", true, true);
			String screenName = screenNode.getAttributeByName("name");
			screenName = screenName.toLowerCase();
			File newDest = new File(homePrototypesDir.getPath()
					+ "/review/screens/" + screenName + ".html");

			if (!newDest.exists()) {
				try {
					FileUtils.write(newDest, html, Charset.defaultCharset());
					FileUtils.forceDelete(screen);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
