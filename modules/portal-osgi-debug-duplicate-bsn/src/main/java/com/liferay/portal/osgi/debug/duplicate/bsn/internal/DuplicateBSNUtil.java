package com.liferay.portal.osgi.debug.duplicate.bsn.internal;

import com.liferay.petra.string.StringBundler;
import org.osgi.framework.Bundle;

import java.util.Arrays;
import java.util.List;

/**
 * DuplicateBSNUtil: This is a static utility class that does the work of finding the duplicate BSNs.
 * The logic is here so it can be shared between the SystemChecker implementation as well as the OSGi
 * Gogo command class.
 *
 * @author dnebinger
 */
public class DuplicateBSNUtil {

	/**
	 * listDuplicateBundleSymbolicNames: Lists all of the duplicate bundle symbolic names found
	 * in the given array of bundles.
	 * @param bundleArray The array of bundles to check for duplicate BSNs.
	 * @return String A string report of duplicate BSNs, or an empty string if no issues were found.
	 */
	public static String listDuplicateBundleSymbolicNames(Bundle... bundleArray) {
		// first convert the array to a list so we can sort it...
		List<Bundle> bundles = Arrays.asList(bundleArray);

		// A sort isn't necessarily required, but it may make more sense in the output listing.

		// sort first by BSN, second by version...
		bundles.sort((Bundle b1, Bundle b2) ->
				(b1.getSymbolicName().equals(b2.getSymbolicName()) ?
						b1.getVersion().compareTo(b2.getVersion()) :
						b1.getSymbolicName().compareTo(b2.getSymbolicName())));

		StringBundler sb = new StringBundler();

		for (int idx = 0; idx < bundles.size() - 1; idx++) {
			Bundle bundle = bundles.get(idx);

			if (bundle.getState() != Bundle.ACTIVE) {
				// do not worry about inactive bundles
				continue;
			}

			for (int jdx = idx + 1; jdx < bundles.size(); jdx++) {
				if (bundles.get(jdx).getState() != Bundle.ACTIVE) {
					// don't worry about this inactive bundle too.
					continue;
				}

				if (bundle.getSymbolicName().equals(bundles.get(jdx).getSymbolicName())) {
					// this is a duplicate name.
					sb.append("\nBundle {id: ");
					sb.append(bundle.getBundleId());
					sb.append(", name: ");
					sb.append(bundle.getSymbolicName());
					sb.append(", version: ");
					sb.append(bundle.getVersion());
					sb.append("}\nis duplicate of");
					sb.append("\n\tBundle {id: ");
					sb.append(bundles.get(jdx).getBundleId());
					sb.append(", name: ");
					sb.append(bundles.get(jdx).getSymbolicName());
					sb.append(", version: ");
					sb.append(bundles.get(jdx).getVersion());
					sb.append("}");
				}
			}
		}

		return sb.toString();
	}
}
