

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.*;

class MatchTest {

	@Test
	void test() {
	}

	@Test
	List convertToList() {
		String str = "1,2,3,4,5,6";
		StringTokenizer st = new StringTokenizer(str, ", ");

		List list = new ArrayList(st.countTokens());		
		while(st.hasMoreTokens()) {
			list.add(Integer.parseInt(st.nextToken()));
		}

		return list;
	}
	
	@Test
	void setResult() {
		int[] match = new int[7];
		ArrayList lottos = new ArrayList(6);
		lottos.add(new ArrayList());		lottos.add(new ArrayList());
		lottos.add(new ArrayList());		lottos.add(new ArrayList());
		lottos.add(new ArrayList());		lottos.add(new ArrayList());
		
		for (int i = 0 ; i < 6 ; i++) {
			match[getMatch((List)lottos.get(i))]++;
		}
	}

	@Test
	int getMatch(List numbers) {
		int count = 0;
		for (int i = 0; i < numbers.size(); i++) {
			count += detailCheck((int)numbers.get(i));
		}

		return count;
	}

	@Test
	int detailCheck(int number) {
		List answer = new ArrayList();
		answer.add(1);		answer.add(2);
		answer.add(3);		answer.add(4);
		answer.add(5);		answer.add(6);
		if (answer.contains(number)) return 1;

		return 0;		
	}
}
