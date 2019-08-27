# Duplicate Bundle Symbolic Names SystemChecker

In OSGi, it is perfectly acceptable to have two bundles with the same
Bundle Symbolic Name (BSN) as long as they are different versions. In theory,
this allows a bundle to depend on the new or the old version and still have
its dependencies satisfied.

In practice, though, this is often not what we want as Liferay Administrators.
A duplicate BSN scenario means you might have a module wiring up to the old version
when you really wanted it to wire up to the new version.

And things get worse when you have multiple bundles, some wired to the old
version and some wired to the new version.

Before you know it, things can get a little crazy in there and trying to
debug the strangeness going on in the environment will push your skills to
the limit.

In Liferay 7.2, Liferay introduced a new API called the SystemChecker.
When you start up a 7.2 instance, you'll often see messages like:

```
Available checkers :[Declarative Service Soft Circular Dependency Checker, 
    Declarative Service Unsatisfied Component Checker, Spring Extender Unavailable Component Checker]
Running "Declarative Service Soft Circular Dependency Checker". 
    You can run this by itself with command "ds:softCircularDependency" in gogo shell.
Declarative Service Soft Circular Dependency Checker check result: No issues were found.
Running "Declarative Service Unsatisfied Component Checker". 
    You can run this by itself with command "ds:unsatisfied" in gogo shell.
Declarative Service Unsatisfied Component Checker check result: No issues were found.
Running "Spring Extender Unavailable Component Checker". 
    You can run this by itself with command "dm na" in gogo shell.
Spring Extender Unavailable Component Checker check result: No issues were found.
```
(I've trimmed out some of the stuff from the actual log and added some linefeeds to make it fit better on the page.)

The normal output is going to be just like this, a report with "No issues were found" for each of the SystemChecker implementations.

In case there are found issues, they'll report things like the bundle name, version, etc. Basically all of the details you need to resolve the issue.

So to get back on topic, this is a new module which implements a SystemChecker that will report on duplicate BSNs in your OSGi container.

Normally you should see "No issues were found", but if you have duplicates it will list both bundles and the versions. It will be up to
you to track down the duplicate bundles from the osgi/modules folder and clean them up if necessary.

Oh, and if this gives you a great idea for your own SystemChecker implementation,
I'm glad I could help!

