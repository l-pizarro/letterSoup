 import java.util.Scanner;

class Start{

  public void Init(){
    Scanner input = new Scanner(System.in);

    // REQUEST SOUP AND WORDS FILE
    System.out.print("Ingrese nombre Sopa [sin extension]: ");
    String soupFile  = input.nextLine();
    System.out.print("Ingrese nombre Sopa 2 [sin extension]: ");
    String soupFile2  = input.nextLine();



    LetterSoup soup = new LetterSoup(FileManager.getMatrix(soupFile),FileManager.getMatrix(soupFile2));

    soup.searchCV();

    String[] words = soup.getWords();
    for(String word:words){
      System.out.println(word);
    }
    // WRITE THE SOLUTION FILE
    //FileManager.writeFile(soup.getResults());
  }
}
