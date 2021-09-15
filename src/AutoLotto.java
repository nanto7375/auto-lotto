import java.util.*;

class InputView {
	private int money;		// ������ ��.
	private int count;		// �ζ� ����.
	private int change;		// �Ž��� ��.
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
	private List lottos;	// ���� ���� �ζǵ��� ����.

	public ResultView(InputView input) {
		this.input = input;
	}
	
// �ζ� ���� ���.	
	public void showCount() {
		System.out.println(input.getCount() + "���� �����߽��ϴ�.");
	}

// �ζ� ��ȣ�� ���.
	public void showLottoNumbers(List lottos) {
		this.lottos = lottos;
		Iterator it = lottos.iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
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
		
		result.showCount();								// �ζ� ���� ���.
		result.showLottoNumbers(lotto.getLottos());		// �ζ� ��ȣ�� ���.
	}
}
