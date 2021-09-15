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
	
// ���� �� ��÷��ȣ�� �Է��ϴ� �޼ҵ�.
	public void setAnswer() {
		System.out.println();
		System.out.println("���� �� ��÷��ȣ�� �Է����ּ���.");

		answer = sc.nextLine();
	}
	
	public int getCount() { return count; }
	public int getMoney() { return money; }
	public String getAnswer() { return answer; }
}

class ResultView {
	private InputView input;
	private int[] match;	// match[i] : ��ȣ�� i�� �´� �ζ��� ����.
	private final int[] PRIZE = {0, 0, 0, 5000, 50000, 1500000, 2000000000};

	public ResultView(InputView input) {
		this.input = input;
	}
	
// �ζ� ���� ���.	
	public void showCount() {
		System.out.println(input.getCount() + "���� �����߽��ϴ�.");
	}

// �ζ� ��ȣ�� ���.
	public void showLottoNumbers(List lottos) {
		Iterator it = lottos.iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
	}

	public void showResult(int[] match) {
		this.match = match;

		System.out.println();
		System.out.println("��÷ ���");
		System.out.println("-------");
		System.out.println("3�� ��ġ (" + PRIZE[3] + "��) - " + match[3] + "��");
		System.out.println("4�� ��ġ (" + PRIZE[4] + "��) - " + match[4] + "��");
		System.out.println("5�� ��ġ (" + PRIZE[5] + "��) - " + match[5] + "��");
		System.out.println("6�� ��ġ (" + PRIZE[6] + "��) - " + match[6] + "��");
		System.out.println("�� ���ͷ��� " + getRatio() + "�Դϴ�.");
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
	private List lottos;	// �ζ� ����.
	private List answer;	// ���� �� ��÷��ȣ.
	private int[] match = new int[7];	// match[i] : ��ȣ�� i�� �´� �ζ��� ����.

	public Match(List lottos, String answer) {
		this.lottos = lottos;
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
		
		Match match = new Match(lotto.getLottos(), input.getAnswer());	// ��÷��ȣ Ȯ��.
		result.showResult(match.getMatch());			// ��� ����.
	}
}
