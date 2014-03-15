
import junit.framework.TestCase;

import org.junit.Test;


public class TriangleTest extends TestCase{


	/**
	 * �����ࣺTriangle
	 * ���Է�����isTriangle
	 * 1.�������ǡ���֧���ǣ�
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
	 * �����ࣺTriangle
	 * ���Է�����isTriangle
	 * 1.�������ǡ���֧���ǣ�
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
	 * �����ࣺTriangle
	 * ���Է�����isTriangle
	 * 1.�������ǡ���֧���ǣ�
	 * if((T&&T)&&(F&&T)&&(T&&T)){	  
	 * }	 		
	 */
	@Test
	public void testIsTriangle3() {
		Triangle t = new Triangle(2, -2, 3);
		assertFalse(t.isTriangle(t));
	}
	
	/**
	 * �����ࣺTriangle
	 * ���Է�����isTriangle
	 * 1.�������ǡ���֧���ǣ�
	 * if((T&&T)&&(T&&T)&&(F&&T)){
	 * }	 		
	 */
	@Test
	public void testIsTriangle4() {
		Triangle t = new Triangle(2, 2, -3);
		assertFalse(t.isTriangle(t));
	}
	
	/**
	 * �����ࣺTriangle
	 * ���Է�����isTriangle
	 * 1.�������ǡ���֧���ǣ�
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
	 * �����ࣺTriangle
	 * ���Է�����isTriangle
	 * 1.�������ǡ���֧���ǣ�
	 * if((F&&T)&&(T&&T)&&(T&&T)){
	 * }	 		
	 */
	@Test
	public void testIsTriangle6() {
		Triangle t = new Triangle(-1, 1, 1);
		assertFalse(t.isTriangle(t));
	}
	
	/**
	 * �����ࣺTriangle
	 * ���Է�����getType
	 * 1.�������ǡ���֧���ǣ�
	 *   isTriangle(triangle)��T
	 *   triangle.lborderA == triangle.lborderB&& triangle.lborderB == triangle.lborderC��
	 *   T&&T&&T=T
	 */
	@Test
	public void testGetType1() {
		Triangle t = new Triangle(2, 2, 2);
		assertEquals("�ȱ�������", t.getType(t));
	}
	
	/**
	 * �����ࣺTriangle
	 * ���Է�����getType
	 * 1.�������ǡ���֧���ǣ�
	 *   isTriangle(triangle)��T
	 *   triangle.lborderA == triangle.lborderB&& triangle.lborderB == triangle.lborderC��
	 *   T&&F&&F=F
	 *   (triangle.lborderA != triangle.lborderB)&& (triangle.lborderB != triangle.lborderC)&& (triangle.lborderA != triangle.lborderC)��
	 *   F&&T&&T=F
	 */
	@Test
	public void testGetType2() {
		Triangle t = new Triangle(2, 2, 3);
		assertEquals("����������", t.getType(t));		
	}
	
	/**
	 * �����ࣺTriangle
	 * ���Է�����getType
	 * 1.�������ǡ���֧���ǣ�
	 *   isTriangle(triangle)��T
	 *   triangle.lborderA == triangle.lborderB&& triangle.lborderB == triangle.lborderC��
	 *   F&&F&&F=F
	 *   (triangle.lborderA != triangle.lborderB)&& (triangle.lborderB != triangle.lborderC)&& (triangle.lborderA != triangle.lborderC)��
	 *   T&&T&&T=T
	 */
	@Test
	public void testGetType3() {
		Triangle t = new Triangle(10, 13, 16);		
		assertEquals("���ȱ�������", t.getType(t));		
	}
	
	/**
	 * �����ࣺTriangle
	 * ���Է�����getType
	 * 1.�������ǡ���֧���ǣ�
	 *   isTriangle(triangle)��F
	 */
	@Test
	public void testGetType4() {		
		Triangle t = new Triangle(3, 3, 6);
		assertEquals("����������", t.getType(t));		
	}
	
	/**
	 * �����ࣺTriangle
	 * ���Է�����getType
	 * 1.�������ǡ���֧���ǣ�
	 *   isTriangle(triangle)��F
	 */
	@Test
	public void testGetType5() {
		Triangle t = new Triangle(-2, 2, 2);
		assertEquals("����������", t.getType(t));
	}
	
	/**
	 * �����ࣺTriangle
	 * ���Է�����getType
	 * 1.�������ǡ���֧���ǣ�
	 *   isTriangle(triangle)��T
	 *   triangle.lborderA == triangle.lborderB&& triangle.lborderB == triangle.lborderC��
	 *   F&&T&&F=F
	 *   (triangle.lborderA != triangle.lborderB)&& (triangle.lborderB != triangle.lborderC)&& (triangle.lborderA != triangle.lborderC)��
	 *   T&&F&&T=F
	 */
	@Test
	public void testGetType6() {
		Triangle t = new Triangle(10, 11, 11);		
		assertEquals("����������", t.getType(t));		
	}

	/**
	 * �����ࣺTriangle
	 * ���Է�����getType
	 * 1.�������ǡ���֧���ǣ�
	 *   isTriangle(triangle)��T
	 *   triangle.lborderA == triangle.lborderB&& triangle.lborderB == triangle.lborderC��
	 *   F&&F&&T=F
	 *   (triangle.lborderA != triangle.lborderB)&& (triangle.lborderB != triangle.lborderC)&& (triangle.lborderA != triangle.lborderC)��
	 *   T&&T&&F=F
	 */
	@Test
	public void testGetType7() {
		Triangle t = new Triangle(21, 15, 21);		
		assertEquals("����������", t.getType(t));		
	}

	/**
	 * �����ࣺTriangle
	 * ���Է�����diffOfBorders
	 *  1.�������ǡ���֧���ǣ�
	 *    a > b
	 */
	@Test
	public void testDiffOfBorders() {
		Triangle t = new Triangle(10, 10, 10);
		assertEquals(0, t.diffOfBorders(10, 10));
		assertEquals(5, t.diffOfBorders(10, 5));
	}

	/**
	 * �����ࣺTriangle
	 * ���Է�����getBorders
	 * 1.��串��
	 */
	@Test
	public void testGetBorders() {
		Triangle t = new Triangle(3, 4, 5);
		assertEquals(3, t.getBorders()[0]);
		assertEquals(4, t.getBorders()[1]);
		assertEquals(5, t.getBorders()[2]);
	}

}
