 import java.util.Scanner;

class Start{

  public void Init(){
    Scanner input = new Scanner(System.in);

    // REQUEST SOUP AND WORDS FILE
    System.out.print("Ingrese nombre archivo Sopa [sin extension]: ");
    String soupFile  = input.nextLine();
    System.out.print("Ingrese nombre archivo Palabras [sin extension]: ");
    String wordsFile = input.nextLine();

    LetterSoup soup = new LetterSoup(FileManager.getMatrix(soupFile),FileManager.getWords(wordsFile));
    soup.calculateResults();

    // WRITE THE SOLUTION FILE
    FileManager.writeFile(soup.getResults());
  }
}
