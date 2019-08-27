package com.liferay.portal.osgi.debug.duplicate.bsn.internal.osgi.commands;

import com.liferay.portal.osgi.debug.duplicate.bsn.internal.DuplicateBSNUtil;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * class DuplicateBundleSymbolicNameOSGiCommands: Creates a Gogo shell command out of our
 * little duplicate bundle name check.
 *
 * @author dnebinger
 */
@Component(
		immediate = true,
		property = {"osgi.command.function=duplicatebsns", "osgi.command.scope=osgi"},
		service = DuplicateBundleSymbolicNameOSGiCommands.class
)
public class DuplicateBundleSymbolicNameOSGiCommands {

	/**
	 * duplicatebsns: This is the entry point for the gogo command.
	 */
	public void duplicatebsns() {
		System.out.println(DuplicateBSNUtil.listDuplicateBundleSymbolicNames(_bundleContext.getBundles()));
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	private BundleContext _bundleContext;
}
