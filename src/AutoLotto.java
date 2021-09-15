import java.util.*;

class InputView {
	private int money;		// 지불한 돈.
	private int count;		// 로또 개수.
	private int change;		// 거스름 돈.
	Scanner sc = new Scanner(System.in);

// 금액을 입력하는 메소드.
	public void setMoney() {
		System.out.println("구입 금액을 입력해 주세요.");
		String tmp = sc.nextLine();

		money = Integer.parseInt(tmp);
		count = money / 1000;
		change = money % 1000;
	}
	
	public int getCount() { return count; }
	public int getMoney() { return money; }
}

class ResultView {
	private InputView input;
	private List lottos;	// 여러 장의 로또들의 묶음.

	public ResultView(InputView input) {
		this.input = input;
	}
	
// 로또 개수 출력.	
	public void showCount() {
		System.out.println(input.getCount() + "개를 구매했습니다.");
	}

// 로또 번호들 출력.
	public void showLottoNumbers(List lottos) {
		this.lottos = lottos;
		Iterator it = lottos.iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
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
	}
}
