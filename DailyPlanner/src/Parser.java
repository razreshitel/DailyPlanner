package com.company;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Scanner;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class Parser {
    public static void main(String[] a) throws IOException {
        //BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //String fn[] = new String[20];
        Scanner in = new Scanner(System.in);
        System.out.println("Введите название файла.");
        //fn = br.readLine();
        System.out.println("Читайте ReadMe.txt\n\n" +
                "Выберите вариант:\n" +
                "1 - Показать все записи со всеми атрибутами.\n" +
                "2 - Показать только дату и текст.\n" +
                "3 - Поиск по id.\n" +
                "4 - Добавить запись\n" +
                "5 - Удалить запись по id");

        try {
            // Создается построитель документа
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = documentBuilder.parse("Other.xml");
        //Document document = documentBuilder.parse(fn);
        Node root = document.getDocumentElement();
        NodeList books = root.getChildNodes();

        //int k = 1;

        int k = in.nextInt();
        String fn = "1";
        switch(k) {
            case 1:

                int idd=0;
                System.out.println("\nСписок записей:\n");
                for (int i = 0; i < books.getLength(); i++) {
                    Node book = books.item(i);
                    if (book.getNodeType() != Node.TEXT_NODE) {
//                        idd++;
//                        System.out.println("id: " + idd);
                        NodeList bookProps = book.getChildNodes();
                        //System.out.println(bookProps.getLength());
                        for(int j = 0; j < bookProps.getLength(); j++) {
                            Node bookProp = bookProps.item(j);
                            if (bookProp.getNodeType() != Node.TEXT_NODE /*&& bookProp.getNodeName() != "id"*/) {
                                System.out.println(bookProp.getNodeName() + ":" + bookProp.getChildNodes().item(0).getTextContent());
                            }
                        }
                        System.out.println("===============");
                    }
                }


                break;
            case 2:


                System.out.println("\nСписок записей:\n");
                for (int i = 0; i < books.getLength(); i++) {
                    Node book = books.item(i);
                    if (book.getNodeType() != Node.TEXT_NODE) {
                        NodeList bookProps = book.getChildNodes();
                        //System.out.println(bookProps.getLength());
                        for(int j = 0; j < bookProps.getLength(); j++) {
                            Node bookProp = bookProps.item(j);
                            if (bookProp.getNodeType() != Node.TEXT_NODE && (bookProp.getNodeName() == "Text" || bookProp.getNodeName() == "Date")) {
                                System.out.println(/*bookProp.getNodeName() + ":" +*/ bookProp.getChildNodes().item(0).getTextContent());
                            }
                        }
                        System.out.println("====================");
                    }
                }


                break;
            case 3:


                System.out.println("\nВведите id для поиска:");
                int id = in.nextInt();
                //int id = 2;
                System.out.println("id - " + id);
                System.out.println();
                XPathFactory pathFactory = XPathFactory.newInstance();
                XPath xpath = pathFactory.newXPath();
                XPathExpression expr = xpath.compile("DayilyPlanner/Plan[id=" + id + "]");
                NodeList nodes = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
                //for (int i = 0; i < nodes.getLength(); i++) {
                    Node book = books.item(id*2-1);
                    if (book.getNodeType() != Node.TEXT_NODE) {
                        NodeList bookProps = book.getChildNodes();
                        //System.out.println(bookProps.getLength());
                        for(int j = 0; j < bookProps.getLength(); j++) {
                            Node bookProp = bookProps.item(j);
                            if (bookProp.getNodeType() != Node.TEXT_NODE && bookProp.getNodeName() != "id") {
                                System.out.println(bookProp.getNodeName() + ":" + bookProp.getChildNodes().item(0).getTextContent());

                            }
                        }
                    }
                //}


                break;
            case 4:
                addNewBook(document);
                break;
            case 5:
                System.out.println("\nВведите id для удаления:");
                //int id_d = 1;
                int id_d = in.nextInt();
                XPathFactory xpf = XPathFactory.newInstance();
                XPath xpaths = xpf.newXPath();
                XPathExpression expression = xpaths.compile("DayilyPlanner/Plan[id="+id_d+"]");
                //NodeList nodes = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
                Node b13Node = (Node) expression.evaluate(document, XPathConstants.NODE);
                b13Node.getParentNode().removeChild(b13Node);

                writeDocument(document);
                /*TransformerFactory tf = TransformerFactory.newInstance();
                Transformer t = tf.newTransformer();
                t.transform(new DOMSource(document), new StreamResult(System.out));*/
                break;
            default:
                System.out.println("Ошибка. Введено неверное значение");
        }
        } catch (ParserConfigurationException | IOException | SAXException | XPathExpressionException ex) {
            ex.printStackTrace(System.out);
        }


    }



    private static void addNewBook(Document document) throws TransformerFactoryConfigurationError, DOMException {
        Scanner in = new Scanner(System.in);
        Node root = document.getDocumentElement();
        int new_id = 1;

         char[] Symters = {84, 104, 105, 115, 32, 112, 114, 111, 103, 114, 97, 109, 32, 109, 97, 100, 101, 32, 98
                 , 121, 32, 70, 105, 108, 105, 112, 112, 111, 118, 95, 65, 95, 65};

//        mas_id[0] = 1;  mas_id[1] = 1;

        NodeList books = root.getChildNodes();
        for (int i = 0; i < books.getLength(); i++) {
            Node book = books.item(i);

            if (book.getNodeType() != Node.TEXT_NODE) {
            new_id++;
            }
        }
        for(int i=0; i<33;i++)
            System.out.print(Symters[i]);
        System.out.println("\n Этой записи выдан id: " + new_id + "\n" +
                        "\nЗаполните поля.\n");
        //System.out.println(a);
        // <Book>
        Element book = document.createElement("Plan");
        // <id>
        Element id = document.createElement("id");
        id.setTextContent(Integer.toString(new_id));
        Element name = document.createElement("Name");
        System.out.println("Name: ");
        name.setTextContent(in.nextLine());
        // <Date>
        Element date = document.createElement("Date");
        System.out.println("Date: ");
        date.setTextContent(in.nextLine());
        // <Time>
        Element time = document.createElement("Time");
        System.out.println("Time: ");
        time.setTextContent(in.nextLine());
        // <Place>
        Element place = document.createElement("Place");
        System.out.println("Place: ");
        place.setTextContent(in.nextLine());
        // <Text>
        Element text = document.createElement("Text");
        System.out.println("Text: ");
        text.setTextContent(in.nextLine());

        //внутренние элементы <Book>
        book.appendChild(id);
        book.appendChild(name);
        book.appendChild(date);
        book.appendChild(time);
        book.appendChild(place);
        book.appendChild(text);
        root.appendChild(book);

        writeDocument(document);
    }


    private static void writeDocument(Document document) throws TransformerFactoryConfigurationError {
        try {
            Transformer tr = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(document);
            FileOutputStream fos = new FileOutputStream("other.xml");
            StreamResult result = new StreamResult(fos);
            tr.transform(source, result);
        } catch (TransformerException | IOException e) {
            e.printStackTrace(System.out);
        }
    }
}
