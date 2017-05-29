import java.io.*;
import java.util.ArrayList;

class FileManager{

  public static char[][] getMatrix(String soupFile){
    soupFile = soupFile.replaceAll(" ","") + ".in";
    ArrayList<char[]> matrix = new ArrayList<char[]>();

    try{
      FileReader reading = new FileReader(soupFile);
      BufferedReader buffer = new BufferedReader(reading);
      String line = "";
      while(line != null){
        line = buffer.readLine();
        if(line == null) break;
        matrix.add(line.replaceAll(" ","").toCharArray());
      }
      buffer.close();
    }

    catch(Exception e){
      System.out.println("Error al intentar leer el archivo " + soupFile);
      e.printStackTrace();
    }

    int index = 0;
    char[][] soup = new char[matrix.size()][matrix.get(0).length];
    for(char[] fila:matrix){
      soup[index] = fila;
      index+=1;
    }

    return soup;
  }

  public static String[] getWords(String wordsFile){
    wordsFile = wordsFile.replaceAll(" ","") + ".in";
    ArrayList<String> words = new ArrayList<String>();

    try{
      FileReader reading = new FileReader(wordsFile);
      BufferedReader buffer = new BufferedReader(reading);
      String line = "";

      while(line != null){
        line = buffer.readLine();
        if(line == null) break;
        words.add(line);
      }

      buffer.close();
    }
    catch(Exception e){
      System.out.println("Error al intentar leer el archivo" + wordsFile);
      e.printStackTrace();
    }

    return words.toArray(new String[words.size()]);
  }

  public static void writeFile(String[] results){
    BufferedWriter bw = null;
		FileWriter fw = null;

		try {
			fw = new FileWriter("Solucion.out");
			bw = new BufferedWriter(fw);
      for(String line:results){
        bw.write(line + "\n");
      }
		}
    catch (IOException e) {
			e.printStackTrace();
		}
    finally {
			try {
				if (bw != null)
					bw.close();
				if (fw != null)
					fw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
  }

}
