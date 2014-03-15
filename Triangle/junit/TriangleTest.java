
import junit.framework.TestCase;

import org.junit.Test;


public class TriangleTest extends TestCase{


	/**
	 * 测试类：Triangle
	 * 测试方法：isTriangle
	 * 1.条件覆盖、分支覆盖：
	 * if((T&&T)&&(T&&T)&&(T&&T)){
	 * 	 if(T&&F&&T)
	 *     isTriangle = true;
	 * }	 		
	 */
	@Test
	public void testIsTriangle1() {
		Triangle t = new Triangle(1, 2, 3);
		assertFalse(t.isTriangle(t));
	}

	/**
	 * 测试类：Triangle
	 * 测试方法：isTriangle
	 * 1.条件覆盖、分支覆盖：
	 * if((T&&T)&&(T&&T)&&(T&&T)){
	 * 	 if(T&&T&&T)
	 *     isTriangle = true;
	 * }	 		
	 */
	@Test
	public void testIsTriangle2() {
		Triangle t = new Triangle(2, 2, 3);
		assertTrue(t.isTriangle(t));
	}
		
	/**
	 * 测试类：Triangle
	 * 测试方法：isTriangle
	 * 1.条件覆盖、分支覆盖：
	 * if((T&&T)&&(F&&T)&&(T&&T)){	  
	 * }	 		
	 */
	@Test
	public void testIsTriangle3() {
		Triangle t = new Triangle(2, -2, 3);
		assertFalse(t.isTriangle(t));
	}
	
	/**
	 * 测试类：Triangle
	 * 测试方法：isTriangle
	 * 1.条件覆盖、分支覆盖：
	 * if((T&&T)&&(T&&T)&&(F&&T)){
	 * }	 		
	 */
	@Test
	public void testIsTriangle4() {
		Triangle t = new Triangle(2, 2, -3);
		assertFalse(t.isTriangle(t));
	}
	
	/**
	 * 测试类：Triangle
	 * 测试方法：isTriangle
	 * 1.条件覆盖、分支覆盖：
	 * if((T&&T)&&(T&&T)&&(T&&T)){
	 * 	 if(F&&T&&F)
	 *     isTriangle = true;
	 * }	 		
	 */
	@Test
	public void testIsTriangle5() {
		Triangle t = new Triangle(9, 5, 3);
		assertFalse(t.isTriangle(t));
	}
	
	/**
	 * 测试类：Triangle
	 * 测试方法：isTriangle
	 * 1.条件覆盖、分支覆盖：
	 * if((F&&T)&&(T&&T)&&(T&&T)){
	 * }	 		
	 */
	@Test
	public void testIsTriangle6() {
		Triangle t = new Triangle(-1, 1, 1);
		assertFalse(t.isTriangle(t));
	}
	
	/**
	 * 测试类：Triangle
	 * 测试方法：getType
	 * 1.条件覆盖、分支覆盖：
	 *   isTriangle(triangle)：T
	 *   triangle.lborderA == triangle.lborderB&& triangle.lborderB == triangle.lborderC：
	 *   T&&T&&T=T
	 */
	@Test
	public void testGetType1() {
		Triangle t = new Triangle(2, 2, 2);
		assertEquals("等边三角形", t.getType(t));
	}
	
	/**
	 * 测试类：Triangle
	 * 测试方法：getType
	 * 1.条件覆盖、分支覆盖：
	 *   isTriangle(triangle)：T
	 *   triangle.lborderA == triangle.lborderB&& triangle.lborderB == triangle.lborderC：
	 *   T&&F&&F=F
	 *   (triangle.lborderA != triangle.lborderB)&& (triangle.lborderB != triangle.lborderC)&& (triangle.lborderA != triangle.lborderC)：
	 *   F&&T&&T=F
	 */
	@Test
	public void testGetType2() {
		Triangle t = new Triangle(2, 2, 3);
		assertEquals("等腰三角形", t.getType(t));		
	}
	
	/**
	 * 测试类：Triangle
	 * 测试方法：getType
	 * 1.条件覆盖、分支覆盖：
	 *   isTriangle(triangle)：T
	 *   triangle.lborderA == triangle.lborderB&& triangle.lborderB == triangle.lborderC：
	 *   F&&F&&F=F
	 *   (triangle.lborderA != triangle.lborderB)&& (triangle.lborderB != triangle.lborderC)&& (triangle.lborderA != triangle.lborderC)：
	 *   T&&T&&T=T
	 */
	@Test
	public void testGetType3() {
		Triangle t = new Triangle(10, 13, 16);		
		assertEquals("不等边三角形", t.getType(t));		
	}
	
	/**
	 * 测试类：Triangle
	 * 测试方法：getType
	 * 1.条件覆盖、分支覆盖：
	 *   isTriangle(triangle)：F
	 */
	@Test
	public void testGetType4() {		
		Triangle t = new Triangle(3, 3, 6);
		assertEquals("不是三角形", t.getType(t));		
	}
	
	/**
	 * 测试类：Triangle
	 * 测试方法：getType
	 * 1.条件覆盖、分支覆盖：
	 *   isTriangle(triangle)：F
	 */
	@Test
	public void testGetType5() {
		Triangle t = new Triangle(-2, 2, 2);
		assertEquals("不是三角形", t.getType(t));
	}
	
	/**
	 * 测试类：Triangle
	 * 测试方法：getType
	 * 1.条件覆盖、分支覆盖：
	 *   isTriangle(triangle)：T
	 *   triangle.lborderA == triangle.lborderB&& triangle.lborderB == triangle.lborderC：
	 *   F&&T&&F=F
	 *   (triangle.lborderA != triangle.lborderB)&& (triangle.lborderB != triangle.lborderC)&& (triangle.lborderA != triangle.lborderC)：
	 *   T&&F&&T=F
	 */
	@Test
	public void testGetType6() {
		Triangle t = new Triangle(10, 11, 11);		
		assertEquals("等腰三角形", t.getType(t));		
	}

	/**
	 * 测试类：Triangle
	 * 测试方法：getType
	 * 1.条件覆盖、分支覆盖：
	 *   isTriangle(triangle)：T
	 *   triangle.lborderA == triangle.lborderB&& triangle.lborderB == triangle.lborderC：
	 *   F&&F&&T=F
	 *   (triangle.lborderA != triangle.lborderB)&& (triangle.lborderB != triangle.lborderC)&& (triangle.lborderA != triangle.lborderC)：
	 *   T&&T&&F=F
	 */
	@Test
	public void testGetType7() {
		Triangle t = new Triangle(21, 15, 21);		
		assertEquals("等腰三角形", t.getType(t));		
	}

	/**
	 * 测试类：Triangle
	 * 测试方法：diffOfBorders
	 *  1.条件覆盖、分支覆盖：
	 *    a > b
	 */
	@Test
	public void testDiffOfBorders() {
		Triangle t = new Triangle(10, 10, 10);
		assertEquals(0, t.diffOfBorders(10, 10));
		assertEquals(5, t.diffOfBorders(10, 5));
	}

	/**
	 * 测试类：Triangle
	 * 测试方法：getBorders
	 * 1.语句覆盖
	 */
	@Test
	public void testGetBorders() {
		Triangle t = new Triangle(3, 4, 5);
		assertEquals(3, t.getBorders()[0]);
		assertEquals(4, t.getBorders()[1]);
		assertEquals(5, t.getBorders()[2]);
	}

}
