package com.hzth.myapp.svn;

import java.util.ArrayList;
import java.util.Collection;

import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.wc.SVNClientManager;

public class SvnLog {

	public static void main(String[] args) throws Exception {
		searchLog();
	}

	private static void searchLog() throws Exception {
		String svnRoot = "svn://223.202.64.196:4391/java/diagnosis-scoreanalysis/trunk";
		String username = "tianyale";
		String password = "tyl6632460";
		SVNClientManager manager = SvnUtil.authSvn(svnRoot, username, password);
		SVNRepository repos = manager.createRepository(SVNURL.parseURIEncoded(svnRoot), true);
		Collection<SVNLogEntry> coll = repos.log(new String[] {}, new ArrayList<>(), 0, -1, true, true);
		for (SVNLogEntry log : coll) {
			System.out.println(log.getMessage() + "\t" + log.getRevision());
		}
	}

}
