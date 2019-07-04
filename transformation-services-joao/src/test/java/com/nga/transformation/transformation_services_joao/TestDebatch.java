package com.nga.transformation.transformation_services_joao;

public class TestDebatch {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		DebatchCanonicalImp dbatch = new DebatchCanonicalImp();
		dbatch.splitAndPublish("ZZAVN00210091562167176.62_17.xmltransformed.xml");
	}

}
