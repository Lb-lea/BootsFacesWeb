import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Reindent {
	static int currentIndent = 0;
	static int parameterIndent = 0;
	static boolean insideTag = false;
	static boolean modified = false;

	public static void main(String[] args) throws IOException {
		scanFolder(new File("src/main/webapp"));
	}

	public static void scanFolder(File folder) throws IOException {
		File[] files = folder.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				scanFolder(file);
			} else if (file.getName().endsWith(".xhtml")) {
				if (!file.getName().endsWith("Attributes.xhtml")) {
					String newFile = readFile(file.getAbsolutePath());
					if (newFile != null) {
						writeFile(file.getAbsolutePath(), newFile);
						System.out.println(file.getAbsolutePath());
						System.exit(1);
					}
				}
			}
		}
	}

	public static void writeFile(String filename, String content) throws IOException {
		new File(filename).delete();
		try (FileWriter fw = new FileWriter(filename); BufferedWriter bw = new BufferedWriter(fw)) {
			bw.write(content);
		}
	}

	public static String readFile(String filename) {
		StringBuilder result = new StringBuilder();

		try (FileReader fr = new FileReader(filename); BufferedReader br = new BufferedReader(fr)) {
			currentIndent = 0;
			modified = false;
			String line;

			while ((line = br.readLine()) != null) {
				line = examineLine(line);
				result.append(line);
				result.append('\n');
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (currentIndent == 0 && modified) {
			return result.toString();
		}
		return null;
	}

	private static String examineLine(String line) {
		String originalLine = line;
		line = line.trim();
		if (line.length() == 0) {
			return line;
		}
		String[] close = line.split("</b:|</h:|</p:|</ui:|</f:");
		currentIndent -= close.length - 1;
		if (close.length>1) {
			insideTag = false;
		}
		if (insideTag && (!line.startsWith("<"))) {
			for (int i = 0; i < parameterIndent+1; i++) {
				line = " " + line;
			}
		} else {
			for (int i = 0; i < currentIndent; i++) {
				line = "  " + line;
			}
		}
		System.out.println(currentIndent + line);
		String[] open = line.split("<b:|<h:|<p:|<ui:|<f:");
		currentIndent += open.length - 1;
		if (open.length>1) {
			insideTag = true;
		}
		if (open[open.length-1].contains(">")) {
			insideTag = false;
		}
		if (open.length>1) {
			int pos = line.indexOf("<");
			parameterIndent = line.indexOf(" ", pos);
		}
		String[] close2 = (line+"blub").split("/>");
		if (close2.length>1) {
			insideTag = false;
		}
		currentIndent -= close2.length - 1;
		if (!originalLine.equals(line)) {
			modified = true;
		}
		return line;
	}
}
