import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;


//����Աֻ��һ��Ĭ�ϵģ����Բ鿴������Ϣ�������ϸ��Ϣ�Լ����ߵ���ϸ��Ϣ��ɾ��ͼ�飬ͼ��黹����
//ͼ��Ļ��ܣ���Ŀ�۸�Ӧ����ʾ��ʲô�ط�������Ա�Ͷ��ߵ�¼��Ľ�����ʾͼ����������ͼ�������ܼ۸�

public class  Manager {
	LibraryMClient lmc;
	private static String admi="BManager";
	private static String password="tushuguanli123";
	ALFrame alf=new ALFrame(this);
	
	public Manager(){
		
	}
    public Manager(LibraryMClient lmc){
		this.lmc=lmc;
	}
	public static String getAdmi(){
		return admi;
	}
	public static String getPassword(){
		return password;
	}	
	public void ALoad(){
		alf.lunchframe();
	}	
}
class ALFrame extends Frame{
	Manager manager;
	BorrowRevertBFrame brf=new BorrowRevertBFrame(this);
	
	public ALFrame(Manager manager){
		this.manager=manager;		
	}
	public void lunchframe(){		
		this.setTitle("����Ա��¼");
		this.setBounds(500, 220, 360, 420);
		this.setLayout(null);
		
		//���֪�����ݿ����ж�������¼���������ݿ���Ϣ�ҳ����ж��������Լ��ִ湲���ٱ��飬����������ܼ۸�
		int bnum=0;
		int bsum=0;
		int psum=0;
		try{
	           Class.forName("com.mysql.jdbc.Driver");
	           String url ="jdbc:mysql://localhost:3306/library";
	           Connection conn= DriverManager.getConnection(url,"root","123456");
	           Statement stmt=conn.createStatement();	               
	          String sqlr="select * from book ";		           
	          ResultSet rs=stmt.executeQuery(sqlr);
	       
	           while(rs.next()) {       	   	        	   	
	        	   	 bnum+=1; 
	        	   	 bsum+=rs.getShort("sum");
	        	   	 psum+=rs.getShort("price");
	           }	         
	           rs.close();
         stmt.close();
         conn.close();      
    }catch(Exception e1){
        e1.printStackTrace();
    }
		Font f=new Font("��������",Font.BOLD,20);		
		String str="            ����ͼ��"+bnum+" ��\n               ��"+bsum+" ��\n     �ܼ۸�"+psum+" yuan";
		JTextArea ta=new JTextArea(str,1,2);	
		ta.setFont(f);
		ta.setForeground(Color.RED);
		ta.setBounds(20, 40, 320,110);
		ta.setBackground(Color.CYAN);
		ta.setEditable(false);
		this.add(ta);		
		
//		JButton br=new JButton("������黹");
//		br.addActionListener(new BorrowRevertBook());
//		br.setBounds(30,170,300,40);		
//		this.add(br);
		
		JButton q=new JButton("��ѯͼ��");
		q.setBounds(30,220,300,40);
		q.addActionListener(new QueryBook());
		this.add(q);
		
		JButton d=new JButton("ɾ��ͼ��");
		d.setBounds(30,270,300,40);
		d.addActionListener(new DeleteBook());
		this.add(d);
		
		JButton a=new JButton("¼��ͼ��");
		a.setBounds(30,320,300,40);
		a.addActionListener(new AddBook());
		this.add(a);
		
		JButton u=new JButton("�޸�ͼ��");
		u.setBounds(30,370,300,40);
		u.addActionListener(new UpdateBook());
		this.add(u);
		
		this.addWindowListener(new WindowAdapter() {			
			public void windowClosing(WindowEvent e) {
				manager.alf.dispose();	}			
		});
		this.setResizable(false);		
		setVisible(true);
	}
	class BorrowRevertBook implements ActionListener{		
		public void actionPerformed(ActionEvent e) {
			brf.lunchFrame();
		}		
	}
	class QueryBook implements ActionListener{
		Book b=new Book();
		public void actionPerformed(ActionEvent e) {	
			b.queryBook();
		}		
	}
	class DeleteBook implements ActionListener{
		Book b=new Book();
		public void actionPerformed(ActionEvent e) {	
			b.deleteBook();
		}		
	}
	class AddBook implements ActionListener{
		Book b=new Book();
		public void actionPerformed(ActionEvent e) {	
			b.addBook();
		}		
	}
	class UpdateBook implements ActionListener{
		Book b=new Book();
		public void actionPerformed(ActionEvent e) {	
			b.updateBook();
		}		
	}
	
}

class BorrowRevertBFrame extends JFrame 
{
    JTable table;
    JScrollPane pane;
    TableModel model;  
    ALFrame al;
    //����   ��Ӧ��Ҫ�õ�ѭ���������������ݿ����Ϣ����ֵ��data�����������֪�����ݿ���ж�������Ϣ
    Object[][] data= 
    	{
    		{1,"�������1","persist1","2015-8-23","45","δ�黹" },
    		{2,"�������2","persist2","2015-8-23","45","δ�黹" },
    		{3,"�������3","persist3","2015-8-23","45","δ�黹" },
    		{4,"�������4","persist4","2015-8-23","45","δ�黹" }
    		};        
    //����
    Object[] clomnName = {"���","����","�û���","����ʱ��","�绰����","�黹"};
    public BorrowRevertBFrame(ALFrame al)
    {
        this.al=al;        
    }
    public void lunchFrame()    {    	  
    	
    	 try{    		 
             Class.forName("com.mysql.jdbc.Driver").newInstance();
             String url ="jdbc:mysql://localhost:3306/library";
             Connection conn= DriverManager.getConnection(url,"root","123456");
             Statement stmt=conn.createStatement();
             String sql="select * from brinfo";
             ResultSet rs=stmt.executeQuery(sql);
             int i=0;           
             while(rs.next()) { 
             	//System.out.print(rs.getString("bn"));
             	data[i][0]=rs.getString("bn");
             	data[i][1]=rs.getString("bname");
             	data[i][2]=rs.getString("username");
             	data[i][3]=rs.getShort("borrowtime");
             	data[i][4]=rs.getString("phonenum");
             	data[i][5]=rs.getString("bn"); 
             	i++;           	
             }   
             
             rs.close();
             stmt.close();
             conn.close();
          
        }catch(Exception e){
            e.printStackTrace();
        }
     
        
		//��ʼ��TableModel��ʹ������data������clomnName
    	 model = new DefaultTableModel(data, clomnName);
        //ʹ�������model��Ϊ������ʼ��JTabel
        table = new JTable(model);
        
        //��ʼ�������б��
        JComboBox box = new JComboBox();
        box.addItem("δ�黹");
        box.addItem("ȷ�Ϲ黹"); 
        box.setEditable(true);
        box.addItemListener(new ItemListener(){  
        	public void itemStateChanged(ItemEvent e) {		
        		String bn;
				if(box.getSelectedIndex()==1){		
					bn=""+e.getItem();					
					try{    		 
			             Class.forName("com.mysql.jdbc.Driver").newInstance();
			             String url ="jdbc:mysql://localhost:3306/library";
			             Connection conn= DriverManager.getConnection(url,"root","123456");
			             Statement stmt=conn.createStatement();
			             String sql="delete  from brinfo where bn='"+bn+"'";	
			             stmt.execute(sql);
			             stmt.close();
			             conn.close();
			          
			        }catch(Exception e1){
			            e1.printStackTrace();
			        }
			     
					System.out.print("ɾ��������¼");
				}				
			}        	
        });
        //��ȡ�Ա���һ�ж���
        TableColumn d = table.getColumn("�黹");        
        //�������б�������༭��
        DefaultCellEditor dce = new DefaultCellEditor(box);         
        d.setCellEditor(dce);
        
        //��ʼ��JScrollPane
        pane = new JScrollPane();
        //��table��ӵ�JScrollPane�� ����ʹ������ĵ�һ�ַ����������
        //ʹ�õ�һ���Ȼ��JViewPort����Ȼ�������
        pane.getViewport().add(table);
        this.setTitle("������黹");
        this.getContentPane().add(pane);
        //setSize(600,300);//����Frame�Ĵ�С
        this.setBounds(350,200,600,300);
        setResizable(false);//���ô��ڲ����Ե�����С      
        setVisible(true);//���ô�����ʾ����
    }

}










