package com.forest.test;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.junit.Test;

public class WordSplitTest {
	@Test
	public void testStandard() throws IOException {
		// ����һ����׼����������
		Analyzer analyzer = new StandardAnalyzer();
		// ���tokenStream����
		// ��һ����������������������һ��
		// �ڶ���������Ҫ�������ı�����
		TokenStream tokenStream = analyzer.tokenStream("test",
				"The Spring Framework provides a comprehensive programming and configuration model.");
		// ���һ�����ã����Ի��ÿ���ؼ���
		CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
		// ���һ��ƫ���������ã���¼�˹ؼ��ʵĿ�ʼλ���Լ�����λ��
		OffsetAttribute offsetAttribute = tokenStream.addAttribute(OffsetAttribute.class);
		// ��ָ��������б��ͷ��
		tokenStream.reset();
		// �����ؼ����б�ͨ��incrementToken�����ж��б��Ƿ����
		while (tokenStream.incrementToken()) {
			// �ؼ��ʵ���ʼλ��
			System.out.println("start->" + offsetAttribute.startOffset());
			// ȡ�ؼ���
			System.out.println(charTermAttribute);
			// ����λ��
			System.out.println("end->" + offsetAttribute.endOffset());
		}
		tokenStream.close();
	}
	
}
