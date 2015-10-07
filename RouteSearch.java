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

class RouteSearch {
	public static void main(String args[]) {

		List<String> lineList = new  ArrayList<>();
		int mapCount = 0;
		int moneyCount = 0;
		int mapX = 0;
		int mapY = 0;
		char map[][];
		int mapCountArray[][];
		int moneyCountArray[][];
		int resultCount = 0;
		int resultMoney = 0;

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

	    for (int y = 0; y < lineList.size(); y++) {
	    	for (int x = 0; x < lineList.get(y).length(); x++) {
	    		int countLeft = (x > 0) ? mapCountArray[y][x-1] : 0;
        		int countTop  = (y > 0) ? mapCountArray[y-1][x] : 0;

        		int moneyLeft  = (x > 0) ? moneyCountArray[y][x-1] : 0;
        		int moneyTop   = (y > 0) ? moneyCountArray[y-1][x] : 0;

	    		char charAt = lineList.get(y).charAt(x);

	    		map[y][x] = charAt;

	    		if (charAt == 'S') {
	    			mapCount = 1;
	    			moneyCount = 0;
	    		} else if (charAt == '-')  {
	    			mapCount = countLeft;
	    			moneyCount = moneyLeft;
	    		} else if (charAt == '|') {
	    			mapCount = countTop;
	    			moneyCount = moneyTop;
	    		} else if (charAt == '+') {
	    			mapCount = countLeft + countTop;
	    			moneyCount = (moneyLeft > moneyTop) ? moneyLeft : moneyTop;
	    		} else if (charAt == 'G') {
	    			mapCount = countLeft + countTop;
	    			moneyCount = (moneyLeft > moneyTop) ? moneyLeft : moneyTop;
	    			resultCount = mapCount;
	    			resultMoney = moneyCount;
	    			// ゴール位置を保存
	    			mapY = y;
	    			mapX = x;
	    			break;
	    		} else if (charAt == ' ') {
	    			mapCount = 0;
	    			moneyCount = -1;
	    		} else if (charAt == 'x') {
	    			mapCount = 0;
	    			moneyCount = -1;
	    		}else if (charAt == '*') {
	    			// とりあえず
	    			mapCount = countLeft + countTop;
	    			moneyCount = (moneyLeft > moneyTop) ? moneyLeft : moneyTop;
	    		} else {
	    			String money_str = String.valueOf(charAt);	    			
	    			moneyCount = (moneyCount < 0) ? 0 : moneyCount;
	    			moneyCount += Integer.valueOf(money_str);

	    			charAt = (y > 0) ? lineList.get(y - 1).charAt(x) : lineList.get(y + 1).charAt(x);
	    			mapCount = (charAt == '|') ? countTop : countLeft;
	    		}

	    		mapCountArray[y][x] = mapCount;
	    		moneyCountArray[y][x] = moneyCount;
	    	}
	    }

	    // Debug
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

	    map[mapY][mapX] = '*';
	    while(true) {
	    	int moneyLeft = (mapX > 0) ? moneyCountArray[mapY][mapX - 1] : -1;
	    	int moneyTop  = (mapY > 0) ? moneyCountArray[mapY - 1][mapX] : -1;

    		if (moneyTop >= moneyLeft) {
    			if (map[mapY - 1][mapX] == 'S' || map[mapY - 1][mapX] == '-' || map[mapY - 1][mapX] == '+' || map[mapY - 1][mapX] == '|') {
    				map[mapY - 1][mapX] = '*';
    			}
    			mapY--;
    		} else {
    			if (map[mapY][mapX - 1] == 'S' || map[mapY][mapX - 1] == '-' || map[mapY][mapX - 1] == '+' || map[mapY][mapX - 1] == '|') {
    				map[mapY][mapX - 1] = '*';
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