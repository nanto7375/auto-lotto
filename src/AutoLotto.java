import java.util.*;

class InputView {
	Scanner sc = new Scanner(System.in);
	private int money;		// ������ ��.
	private int count;		// �ζ� ����.
	private int change;		// �Ž��� ��.
	private String answer;	// ��÷��ȣ.

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

	public void setAnswer() {
		System.out.println();
		System.out.println("���� �� ��÷��ȣ�� �Է����ּ���.");

		answer = sc.nextLine();
	}
	public String getAnswer() { return answer; }
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

class Match {
	private List lottos;
	private List answer;	// ���� �� ��÷��ȣ.
	private int[] match = new int[7];

	public Match(Lotto lotto, String answer) {
		lottos = lotto.getLottos();
		this.answer = convertToList(answer);

		setResult();
	}
	
// �Է� ���� ��÷��ȣ�� List�������� �����ϱ� ���� �޼ҵ�.
	private List convertToList(String str) {
		StringTokenizer st = new StringTokenizer(str, ", ");

		List list = new ArrayList(st.countTokens());		
		while(st.hasMoreTokens()) {
			list.add(Integer.parseInt(st.nextToken()));
		}

		return list;
	}

// ��÷ Ȯ���� ���Ͽ� �ζǹ������� �ϳ��� ������ �޼ҵ�.
	private void setResult() {
		for (int i = 0 ; i < lottos.size() ; i++) {
			match[getMatch((List)lottos.get(i))]++;
		}
	}
// ������ �ζ��� ��ȣ���� �ϳ��� �����ϴ� �޼ҵ�.
	private int getMatch(List numbers) {
		int count = 0;
		for (int i = 0; i < numbers.size(); i++) {
			count += detailCheck((int)numbers.get(i));
		}

		return count;
	}

// ��÷��ȣ�� ��ȣ�� �ϳ��� ���ϴ� �޼ҵ�.
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

		input.setMoney();								// �ݾ� �����ϱ�.
		if (input.getCount() == 0) {
			System.out.println("�ζǸ� �������� ���߽��ϴ�.");
			return;
		}

		Lotto lotto = new Lotto(input);					// �ζ� ��ȣ�� ����.
		
		result.showCount();								// �ζ� ���� ���.
		result.showLottoNumbers(lotto.getLottos());		// �ζ� ��ȣ�� ���.

		input.setAnswer();								// ���� �� ��÷��ȣ �Է�.
		
		Match match = new Match(lotto, input.getAnswer());
	}
}
