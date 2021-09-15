import java.util.*;

class InputView {
	Scanner sc = new Scanner(System.in);
	private int money;		// 지불한 돈.
	private int count;		// 로또 개수.
	private int change;		// 거스름 돈.
	private String answer;	// 당첨번호.

// 금액을 입력하는 메소드.
	public void setMoney() {
		System.out.println("구입 금액을 입력해 주세요.");
		String tmp = sc.nextLine();

		money = Integer.parseInt(tmp);
		count = money / 1000;
		change = money % 1000;
	}
	
// 지난 주 당첨번호를 입력하는 메소드.
	public void setAnswer() {
		System.out.println();
		System.out.println("지난 주 당첨번호를 입력해주세요.");

		answer = sc.nextLine();
	}
	
	public int getCount() { return count; }
	public int getMoney() { return money; }
	public String getAnswer() { return answer; }
}

class ResultView {
	private InputView input;
	private int[] match;	// match[i] : 번호가 i개 맞는 로또의 개수.
	private final int[] PRIZE = {0, 0, 0, 5000, 50000, 1500000, 2000000000};

	public ResultView(InputView input) {
		this.input = input;
	}
	
// 로또 개수 출력.	
	public void showCount() {
		System.out.println(input.getCount() + "개를 구매했습니다.");
	}

// 로또 번호들 출력.
	public void showLottoNumbers(List lottos) {
		Iterator it = lottos.iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
	}

	public void showResult(int[] match) {
		this.match = match;

		System.out.println();
		System.out.println("당첨 통계");
		System.out.println("-------");
		System.out.println("3개 일치 (" + PRIZE[3] + "원) - " + match[3] + "개");
		System.out.println("4개 일치 (" + PRIZE[4] + "원) - " + match[4] + "개");
		System.out.println("5개 일치 (" + PRIZE[5] + "원) - " + match[5] + "개");
		System.out.println("6개 일치 (" + PRIZE[6] + "원) - " + match[6] + "개");
		System.out.println("총 수익률은 " + getRatio() + "입니다.");
	}

	private double getRatio() {
		int sum = 0;
		for (int i = 3; i <= 6; i++) {
			sum += match[i] * PRIZE[i];
		}

		double result = (double)sum / input.getMoney();
		return Math.round(result * 100) / 100.0;
	}
}

class Lotto {
	private List numbers;	// 1~45까지의 숫자 저장.
	private List lottos;	// 여러 개의 로또들의 묶음.

	public Lotto(InputView input) {
		lottos = new ArrayList(input.getCount());

		for (int i = 0; i < input.getCount() ; i++) {
			setNumbers();
			Collections.shuffle(numbers);
			List lotto = numbers.subList(0, 6);
			Collections.sort(lotto);
			lottos.add(lotto);
		}
	}
	
// 새로운 로또 번호를 위해 numbers에 저장되는 주소를 바꿔준다.	
	private void setNumbers() {
		numbers = new ArrayList(45);
		for (int i = 0; i < 45 ; i++) {
			numbers.add(i + 1);
		}
	}

	public List getLottos() { return lottos; }
}

class Match {
	private List lottos;	// 로또 묶음.
	private List answer;	// 지난 주 당첨번호.
	private int[] match = new int[7];	// match[i] : 번호가 i개 맞는 로또의 개수.

	public Match(List lottos, String answer) {
		this.lottos = lottos;
		this.answer = convertToList(answer);

		setResult();
	}
	
// 입력 받은 당첨번호를 List형식으로 변경하기 위한 메소드.
	private List convertToList(String str) {
		StringTokenizer st = new StringTokenizer(str, ", ");

		List list = new ArrayList(st.countTokens());		
		while(st.hasMoreTokens()) {
			list.add(Integer.parseInt(st.nextToken()));
		}

		return list;
	}

// 당첨 확인을 위하여 로또묶음에서 하나씩 꺼내는 메소드.
	private void setResult() {
		for (int i = 0 ; i < lottos.size() ; i++) {
			match[getMatch((List)lottos.get(i))]++;
		}
	}
// 선택한 로또의 번호들을 하나씩 추출하는 메소드.
	private int getMatch(List numbers) {
		int count = 0;
		for (int i = 0; i < numbers.size(); i++) {
			count += detailCheck((int)numbers.get(i));
		}

		return count;
	}

// 당첨번호와 번호를 하나씩 비교하는 메소드.
	private int detailCheck(int number) {
		if (answer.contains(number)) return 1;

		return 0;		
	}

	public int[] getMatch() { return match; }
}

public class AutoLotto {
	public static void main(String[] args) {
		InputView input = new InputView();
		ResultView result = new ResultView(input);

		input.setMoney();								// 금액 지불하기.
		if (input.getCount() == 0) {
			System.out.println("로또를 구입하지 못했습니다.");
			return;
		}

		Lotto lotto = new Lotto(input);					// 로또 번호들 생성.
		
		result.showCount();								// 로또 개수 출력.
		result.showLottoNumbers(lotto.getLottos());		// 로또 번호들 출력.

		input.setAnswer();								// 지난 주 당첨번호 입력.
		
		Match match = new Match(lotto.getLottos(), input.getAnswer());	// 당첨번호 확인.
		result.showResult(match.getMatch());			// 통계 보기.
	}
}
