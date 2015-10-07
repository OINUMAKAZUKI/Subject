import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.lang.Character;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

class Main {
  public static void main(String args[]) {
    List<String> lineList = new  ArrayList<>();
    int mapCount = 0;
    int moneyCount = 0;
    int gCount = 0;
    int mapX = 0;
    int mapY = 0;
    char map[][];
    int mapCountArray[][];
    int moneyCountArray[][];
    int gCountArray[][];
    int resultCount = 0;
    int resultMoney = 0;
    int resultGCount = 0;
    int point = 0;

    //マップ読み込み
    try{
      BufferedReader reader = new BufferedReader(
        new InputStreamReader(new FileInputStream("file.txt"), "UTF-8"));
      while (true) {
          String line = reader.readLine();
          if (line == null)
              break;
          System.out.print(line + "\n");
          lineList.add(line);
      }
      }catch(FileNotFoundException e) {
        System.out.println(e);
      }catch(IOException e) {
        System.out.println(e);
      }

      map = new char[lineList.size()][lineList.get(0).length()];
      mapCountArray = new int[lineList.size()][lineList.get(0).length()];
      moneyCountArray = new int[lineList.size()][lineList.get(0).length()];
      gCountArray = new int[lineList.size()][lineList.get(0).length()];

      for (int y = 0; y < lineList.size(); y++) {
        for (int x = 0; x < lineList.get(y).length(); x++) {
            int countLeft = (x > 0) ? mapCountArray[y][x - 1] : 0;
            int countTop  = (y > 0) ? mapCountArray[y - 1][x] : 0;

            int moneyLeft  = (x > 0) ? moneyCountArray[y][x - 1] : 0;
            int moneyTop   = (y > 0) ? moneyCountArray[y - 1][x] : 0;

            int gLeft  = (x > 0) ? moneyCountArray[y][x - 1] : 0;
            int gTop   = (y > 0) ? moneyCountArray[y - 1][x] : 0;

            char charAt = lineList.get(y).charAt(x);

            map[y][x] = charAt;

            if (charAt == 'S') {
              mapCount = 1;
              moneyCount = 0;
              gCount = 0;
            } else if (charAt == '-')  {
              mapCount = countLeft;
              moneyCount = moneyLeft;
              gCount = gLeft;
            } else if (charAt == '|') {
              mapCount = countTop;
              moneyCount = moneyTop;
              gCount = gTop;
            } else if (charAt == '+') {
              mapCount = countLeft + countTop;
              moneyCount = (moneyLeft > moneyTop) ? moneyLeft : moneyTop;
              gCount = (gLeft > gTop) ? gLeft : gTop;
            } else if (charAt == 'G') {
              mapCount = countLeft + countTop;
              moneyCount = (moneyLeft > moneyTop) ? moneyLeft : moneyTop;
              gCount = (gLeft > gTop) ? gLeft : gTop;

              resultCount = mapCount;
              resultMoney = moneyCount;
              resultGCount = gCount;
              // ゴール位置を保存
              mapY = y;
              mapX = x;
              break;
            } else if (charAt == ' ') {
              mapCount = 0;
              moneyCount = -1;
              gCount = 0;
            } else if (charAt == 'x') {
              mapCount = 0;
              moneyCount = -1;
              gCount = 0;
            } else if (charAt == '*') {
              // とりあえず
              mapCount = countLeft + countTop;
              moneyCount = (moneyLeft > moneyTop) ? moneyLeft : moneyTop;
              gCount = (gLeft > gTop) ? gLeft + 1 : gTop + 1;
            } else {
              // 数字の場合
              String money_str = String.valueOf(charAt);
              moneyCount = (moneyCount < 0) ? 0 : moneyCount;
              moneyCount += Integer.valueOf(money_str);

              charAt = (y > 0) ? lineList.get(y - 1).charAt(x) : lineList.get(y + 1).charAt(x);
              mapCount = (charAt == '|') ? countTop : countLeft;
              gCount = (charAt == '|') ? gTop : gLeft;
            }

            mapCountArray[y][x] = mapCount;
            moneyCountArray[y][x] = moneyCount;
            gCountArray[y][x] = gCount;
        }
      }
      // Map
      for (int y = 0; y < lineList.size(); y++) {
        for (int x = 0; x < lineList.get(y).length(); x++) {
          String space = " ";
          if (mapCountArray[y][x] > 9 || mapCountArray[y][x] < 0) {
            space = "";
          }
          System.out.print(space + moneyCountArray[y][x]);
        }
        System.out.print("\n");
      }

      System.out.print("\n");

      for (int y = 0; y < lineList.size(); y++) {
        for (int x = 0; x < lineList.get(y).length(); x++) {
          String space = " ";
          if (moneyCountArray[y][x] > 9 || moneyCountArray[y][x] < 0) {
            space = "";
          }
          System.out.print(space + moneyCountArray[y][x]);
        }
        System.out.print("\n");
      }

      System.out.print("\n");
      // G Num
      for (int y = 0; y < lineList.size(); y++) {
        for (int x = 0; x < lineList.get(y).length(); x++) {
          String space = " ";
          if (gCountArray[y][x] > 9 || gCountArray[y][x] < 0) {
            space = "";
          }
          System.out.print(space + gCountArray[y][x]);
        }
        System.out.print("\n");
      }

      map[mapY][mapX] = '@';
      while(true) {
        int moneyLeft = (mapX > 0) ? moneyCountArray[mapY][mapX - 1] : 0;
        int moneyTop  = (mapY > 0) ? moneyCountArray[mapY - 1][mapX] : 0;

        System.out.print(" [" + mapY + " " + mapX + "]");

        if (moneyTop <= moneyLeft) {
          mapY = (mapY == 0) ? mapY + 1 : mapY;
          if (map[mapY - 1][mapX] == 'S' || map[mapY - 1][mapX] == '-' || map[mapY - 1][mapX] == '+' || map[mapY - 1][mapX] == '|') {
            map[mapY - 1][mapX] = '@';
          }
          mapY--;
        } else {
          if (map[mapY][mapX - 1] == 'S' || map[mapY][mapX - 1] == '-' || map[mapY][mapX - 1] == '+' || map[mapY][mapX - 1] == '|') {
            map[mapY][mapX - 1] = '@';
          }
          mapX--;
        }

        if (map[mapY][mapX] == 'S' || mapX == 0 && mapY == 0) {
          break;
        }
      }

      // 結果を表示
      for (int y = 0; y < lineList.size(); y++) {
        for (int x = 0; x < lineList.get(y).length(); x++) {
          System.out.print(map[y][x]);
        }
        System.out.print("\n");
      }

      System.out.println(resultCount + "通り中" + resultMoney + "万円を拾うルート");
  }
}
