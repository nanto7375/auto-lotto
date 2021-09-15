import java.util.*;

class InputView {
	private int money;
	private int count;
	private int change;
	Scanner sc = new Scanner(System.in);

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

	public ResultView(InputView input) {
		this.input = input;
	}
}

public class AutoLotto {
	public static void main(String[] args) {
		InputView input = new InputView();
		ResultView result = new ResultView(input);

		input.setMoney();
		if (input.getCount() == 0) {
			System.out.println("로또를 구입하지 못했습니다.");
			return;
		}
		
	}
}
