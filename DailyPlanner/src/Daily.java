import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;

public class Daily {
        public static void main(String args[])  {
            try {
                // Создается построитель документа
                DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                // Создается дерево DOM документа из файла
                Document document = documentBuilder.parse("Cat2.xml");
                //Document document = documentBuilder.parse(fn);


                // Получаем корневой элемент
                Node root = document.getDocumentElement();
                NodeList books = root.getChildNodes();


            int k = 1;
            switch(k) {
                case 1:
                    System.out.println("Список записей:");
                    // Просматриваем все подэлементы корневого - т.е. книги

                    for (int i = 0; i < books.getLength(); i++) {
                        Node book = books.item(i);
                        // Если нода не текст, то это книга - заходим внутрь
                        if (book.getNodeType() != Node.TEXT_NODE) {
                            NodeList bookProps = book.getChildNodes();
                            //System.out.println(bookProps.getLength());
                            for(int j = 0; j < bookProps.getLength(); j++) {
                                Node bookProp = bookProps.item(j);
                                // Если нода не текст, то это один из параметров книги - печатаем
                                if (bookProp.getNodeType() != Node.TEXT_NODE) {
                                    System.out.println(bookProp.getNodeName() + ":" + bookProp.getChildNodes().item(0).getTextContent());
                                }
                            }
                            System.out.println("===============");
                        }
                    }
                    break;
                case 2:
                    break;
                default:
                    System.out.println("Ошибка. Введено неверное значение");
            }



            } catch (ParserConfigurationException | IOException | SAXException ex) {
                ex.printStackTrace(System.out);
            }
        }
}
