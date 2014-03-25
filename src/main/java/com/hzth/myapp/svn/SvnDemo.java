package com.hzth.myapp.svn;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

public class SvnDemo {

	public static void main(String[] args) throws Exception {
		// svnCheckoutAll();
		// copyToTarget();
		String projectName = "dc-addresslist";
		svnCheckout(projectName);
		copyToTarget(projectName);
		// findFile();
	}

	private static void copyToTarget() throws Exception {
		File rootFile = new File("E:/dcall");
		for (File file : rootFile.listFiles()) {
			copyToTarget(file.getName());
		}
	}

	private static void copyToTarget(String projectName) throws Exception {
		File file = new File("E:/dcall/" + projectName);
		System.out.println("复制：" + file.getAbsolutePath());
		List<File> files = new ArrayList<File>();
		getFiles(files, new File(file.getAbsoluteFile() + "/doc"));
		getFiles(files, new File(file.getAbsoluteFile() + "/src"));
		getFiles(files, new File(file.getAbsoluteFile() + "/webapp"));
		for (File srcFile : files) {
			File targetFile = new File(srcFile.getAbsolutePath().replace(file.getAbsolutePath(), "E:\\workspaceall\\dc"));
			FileUtils.copyFile(srcFile, targetFile);
		}
	}

	private static void getFiles(List<File> files, File file) {
		for (File f : file.listFiles()) {
			if (f.isDirectory()) {
				getFiles(files, f);
			} else {
				files.add(f);
			}
		}
	}

	@SuppressWarnings("unchecked")
	private static void svnCheckoutAll() throws Exception {
		String url = "https://192.168.1.8/svn/dc-v3/";
		String name = "tianyale";
		String password = "tianyale";
		SVNRepository repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(url));
		ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(name, password);
		repository.setAuthenticationManager(authManager);
		Collection<SVNDirEntry> all = repository.getDir("/java", -1, null, (Collection<SVNDirEntry>) null);
		List<SVNDirEntry> svnDirEntries = new ArrayList<SVNDirEntry>();
		List<String> ignNames = new ArrayList<String>();
		ignNames.add("dc-all");
		ignNames.add("dc-sp-example");
		ignNames.add("dc-materials");
		// ignNames.add("dc-cifagent");
		ignNames.add("product-v3");
		for (SVNDirEntry entry : all) {
			// System.out.println(entry.getURL().toDecodedString());
			if (!ignNames.contains(entry.getName())) {
				svnDirEntries.add(entry);
			}
		}
		System.out.println(all.size());

		SVNClientManager manager = authSvn(url, name, password);
		for (SVNDirEntry entry : svnDirEntries) {
			SVNURL aaa = entry.getURL();
			System.out.println("下载：" + aaa.toDecodedString());
			checkout(manager, aaa.appendPath("/trunk", true), SVNRevision.HEAD, new File("E:/dcall/" + entry.getName()));
		}
		System.out.println("下载完成!" + svnDirEntries.size());
	}

	@SuppressWarnings("unchecked")
	private static void svnCheckout(String projectName) throws Exception {
		String url = "https://192.168.1.8/svn/dc-v3/";
		String name = "tianyale";
		String password = "tianyale";
		SVNRepository repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(url));
		ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(name, password);
		repository.setAuthenticationManager(authManager);
		Collection<SVNDirEntry> all = repository.getDir("/java", -1, null, (Collection<SVNDirEntry>) null);
		SVNClientManager manager = authSvn(url, name, password);
		for (SVNDirEntry entry : all) {
			if (entry.getName().equals(projectName)) {
				SVNURL aaa = entry.getURL();
				System.out.println("下载：" + aaa.toDecodedString());
				checkout(manager, aaa.appendPath("/trunk", true), SVNRevision.HEAD, new File("E:/dcall/" + entry.getName()));
			}
		}
	}

	public static void setupLibrary() {
		DAVRepositoryFactory.setup();
		SVNRepositoryFactoryImpl.setup();
		FSRepositoryFactory.setup();
	}

	/**
	 * 验证登录svn
	 */
	public static SVNClientManager authSvn(String svnRoot, String username, String password) {
		// 初始化版本库
		setupLibrary();

		// 创建库连接
		SVNRepository repository = null;
		try {
			repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(svnRoot));
		} catch (SVNException e) {
			e.printStackTrace();
			return null;
		}

		// 身份验证
		ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(username, password);

		// 创建身份验证管理器
		repository.setAuthenticationManager(authManager);

		DefaultSVNOptions options = SVNWCUtil.createDefaultOptions(true);
		SVNClientManager clientManager = SVNClientManager.newInstance(options, authManager);
		return clientManager;
	}

	/**
	 * recursively checks out a working copy from url into wcDir
	 * 
	 * @param clientManager
	 * @param url
	 *            a repository location from where a Working Copy will be checked out
	 * @param revision
	 *            the desired revision of the Working Copy to be checked out
	 * @param destPath
	 *            the local path where the Working Copy will be placed
	 * @param depth
	 *            checkout的深度，目录、子目录、文件
	 * @return
	 * @throws SVNException
	 */
	public static long checkout(SVNClientManager clientManager, SVNURL url, SVNRevision revision, File destPath) {
		SVNUpdateClient updateClient = clientManager.getUpdateClient();
		updateClient.setIgnoreExternals(false);
		try {
			return updateClient.doCheckout(url, destPath, revision, revision, SVNDepth.INFINITY, false);
		} catch (SVNException e) {
			e.printStackTrace();
		}
		return 0;
	}

	private static void findFile() throws Exception {
		String url = "https://192.168.1.8/svn/dc-v3/";
		String name = "tianyale";
		String password = "tianyale";
		SVNRepository repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(url));
		ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(name, password);
		repository.setAuthenticationManager(authManager);
		Collection<SVNDirEntry> all = repository.getDir("/java", -1, null, (Collection<SVNDirEntry>) null);
		SVNClientManager manager = authSvn(url, name, password);

		for (SVNDirEntry entry : all) {

		}
	}
}
