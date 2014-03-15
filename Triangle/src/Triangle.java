public class Triangle {
	// 定义三角形的三边
	protected long lborderA = 0;

	protected long lborderB = 0;

	protected long lborderC = 0;

	// 构造函数
	public Triangle(long lborderA, long lborderB, long lborderC) {

		this.lborderA = lborderA;

		this.lborderB = lborderB;

		this.lborderC = lborderC;

	}

	/**
	 * 判断是否是三角形
	 * 
	 * 是返回true；不是返回false
	 */
	public boolean isTriangle(Triangle triangle) {
		boolean isTriangle = false;

		// 判断边界，大于0 小于或等于Long.MAX_VALUE，出界返回false
		if ((triangle.lborderA > 0 && triangle.lborderA <= Long.MAX_VALUE)
				&& (triangle.lborderB > 0 && triangle.lborderB <= Long.MAX_VALUE)
				&& (triangle.lborderC > 0 && triangle.lborderC <= Long.MAX_VALUE)) {

			// 判断两边之差小于第三边
			if (diffOfBorders(triangle.lborderA, triangle.lborderB) < triangle.lborderC
					&& diffOfBorders(triangle.lborderB, triangle.lborderC) < triangle.lborderA
					&& diffOfBorders(triangle.lborderC, triangle.lborderA) < triangle.lborderB) {
				isTriangle = true;
			}

		}
		return isTriangle;
	}

	/**
	 * 判断三角形类型
	 * 
	 * 只有两条边相等的三角形返回字符串“等腰三角形”； 三边均相等的三角形返回字符串“等边三角形”； 三边均不等的三角形返回字符串“不等边三角形”；
	 */
	public String getType(Triangle triangle) {
		String strType = "不是三角形";

		// 判断是否是三角形
		if (isTriangle(triangle)) {
			// 判断是否是等边三角形
			if (triangle.lborderA == triangle.lborderB
					&& triangle.lborderB == triangle.lborderC) {
				strType = "等边三角形";
			}
			// 判断是否是不等边三角形
			else if ((triangle.lborderA != triangle.lborderB)
					&& (triangle.lborderB != triangle.lborderC)
					&& (triangle.lborderA != triangle.lborderC)) {
				strType = "不等边三角形";
			}
			// 三角形既非三边全部相等，又非全部不等，只能是部分相等即等腰三角形
			else {
				strType = "等腰三角形";
			}
		}

		return strType;
	}

	/**
	 * 计算两边之差的绝对值
	 * 
	 * */
	public long diffOfBorders(long a, long b) {
		return (a > b) ? (a - b) : (b - a);
	}

	/**
	 * 用于获取三角形的边长
	 */
	public long[] getBorders() {
		long[] borders = new long[3];
		borders[0] = this.lborderA;
		borders[1] = this.lborderB;
		borders[2] = this.lborderC;
		return borders;
	}
}