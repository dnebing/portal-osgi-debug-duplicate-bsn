package com.liferay.portal.osgi.debug.duplicate.bsn.internal;


import com.liferay.petra.string.StringBundler;
import com.liferay.portal.osgi.debug.SystemChecker;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * DuplicateBundleSymbolicNameSystemChecker: This is a SystemChecker implementation
 * that checks for duplicate bundle symbolic names in the OSGi container.
 * While technically this is not an error (it is perfectly fine to have two
 * different bundles with the same BSN at different versions), more often
 * than not it happens in error.
 *
 * Knowing that there are two or more bundles with the same BSN, the admin
 * will be free to investigate and resolve if they wish.
 *
 * @author dnebinger
 */
@Component(immediate = true, service = SystemChecker.class)
public class DuplicateBundleSymbolicNameSystemChecker implements SystemChecker {
	@Override
	public String check() {
		return DuplicateBSNUtil.listDuplicateBundleSymbolicNames(_bundleContext.getBundles());
	}

	@Override
	public String getName() {
		return "Duplicate Bundle Symbolic Name Checker";
	}

	@Override
	public String getOSGiCommand() {
		return "osgi:duplicatebsns";
	}

	@Override
	public String toString() {
		return getName();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	private BundleContext _bundleContext;
}
