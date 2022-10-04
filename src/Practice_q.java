public class Practice_q
{
	public static void main(String[] args)
	{
		int value = 0;
		int add = 3;
		int x = 0;
		int y = 0;

		for (int i = 0; i < 20; i++)
		{
			value += add;
			x = value / 10;
			y = value % 10;
			System.out.println(x + "." + y);
		}

		System.out.println("---------------------------");

		int a = 0;
		int b = 0;
		int cnt = 0;
		int[] rule = {3, 6, 9, 2, 5, 8, 1, 4, 7, 0};

		for (int i = 1; i <= 20; i++)
		{
			if (cnt == 3 || cnt == 6 || cnt == 9)
			{
				a++;
			}
			b = rule[cnt];
			System.out.println(a + "." + b);
			cnt++;
			if (cnt == 10)
			{
				cnt = 0;
			}
		}
	}
}
