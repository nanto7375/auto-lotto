import java.util.*;

class InputView {
	private int money;
	private int count;
	private int change;
	Scanner sc = new Scanner(System.in);

// �ݾ��� �Է��ϴ� �޼ҵ�.
	public void setMoney() {
		System.out.println("���� �ݾ��� �Է��� �ּ���.");
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
	
// �ζ� ���� ���.	
	public void showCount() {
		System.out.println(input.getCount() + "���� �����߽��ϴ�.");
	}
}

class Lotto {
	private List numbers;	// 1~45������ ���� ����.
	private List lottos;	// ���� ���� �ζǵ��� ����.

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
// ���ο� �ζ� ��ȣ�� ���� numbers�� ����Ǵ� �ּҸ� �ٲ��ش�.	
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

		input.setMoney();								// �ݾ� �����ϱ�.
		if (input.getCount() == 0) {
			System.out.println("�ζǸ� �������� ���߽��ϴ�.");
			return;
		}

		Lotto lotto = new Lotto(input);					// �ζ� ��ȣ�� ����.
		
		result.showCount();
	}
}
