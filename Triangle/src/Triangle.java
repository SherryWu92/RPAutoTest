public class Triangle {
	// ���������ε�����
	protected long lborderA = 0;

	protected long lborderB = 0;

	protected long lborderC = 0;

	// ���캯��
	public Triangle(long lborderA, long lborderB, long lborderC) {

		this.lborderA = lborderA;

		this.lborderB = lborderB;

		this.lborderC = lborderC;

	}
//test//
	/**
	 * �ж��Ƿ���������
	 * 
	 * �Ƿ���true�����Ƿ���false
	 */
	public boolean isTriangle(Triangle triangle) {
		boolean isTriangle = false;

		// �жϱ߽磬����0 С�ڻ����Long.MAX_VALUE�����緵��false
		if ((triangle.lborderA > 0 && triangle.lborderA <= Long.MAX_VALUE)
				&& (triangle.lborderB > 0 && triangle.lborderB <= Long.MAX_VALUE)
				&& (triangle.lborderC > 0 && triangle.lborderC <= Long.MAX_VALUE)) {

			// �ж�����֮��С�ڵ�����
			if (diffOfBorders(triangle.lborderA, triangle.lborderB) < triangle.lborderC
					&& diffOfBorders(triangle.lborderB, triangle.lborderC) < triangle.lborderA
					&& diffOfBorders(triangle.lborderC, triangle.lborderA) < triangle.lborderB) {
				isTriangle = true;
			}

		}
		return isTriangle;
	}

	/**
	 * �ж�����������
	 * 
	 * ֻ����������ȵ������η����ַ��������������Ρ��� ���߾���ȵ������η����ַ������ȱ������Ρ��� ���߾����ȵ������η����ַ��������ȱ������Ρ���
	 */
	public String getType(Triangle triangle) {
		String strType = "����������";

		// �ж��Ƿ���������
		if (isTriangle(triangle)) {
			// �ж��Ƿ��ǵȱ�������
			if (triangle.lborderA == triangle.lborderB
					&& triangle.lborderB == triangle.lborderC) {
				strType = "�ȱ�������";
			}
			// �ж��Ƿ��ǲ��ȱ�������
			else if ((triangle.lborderA != triangle.lborderB)
					&& (triangle.lborderB != triangle.lborderC)
					&& (triangle.lborderA != triangle.lborderC)) {
				strType = "���ȱ�������";
			}
			// �����μȷ�����ȫ����ȣ��ַ�ȫ�����ȣ�ֻ���ǲ�����ȼ�����������
			else {
				strType = "����������";
			}
		}

		return strType;
	}

	/**
	 * ��������֮��ľ���ֵ
	 * 
	 * */
	public long diffOfBorders(long a, long b) {
		return (a > b) ? (a - b) : (b - a);
	}

	/**
	 * ���ڻ�ȡ�����εı߳�
	 */
	public long[] getBorders() {
		long[] borders = new long[3];
		borders[0] = this.lborderA;
		borders[1] = this.lborderB;
		borders[2] = this.lborderC;
		return borders;
	}
}