/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_durable_elevated.work;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Created 4/26/21 4:22 AM
 */
public class EDP_WorkManager implements WorkManager {
	@NotNull List<WorkList> doneWork = new ArrayList<>();
	@NotNull List<WorkList> jobs     = new ArrayList<>();

	public void addJobs(final WorkList aList) {
		jobs.add(aList);
	}

	@Override
	public void drain() {
		while (true) {
			@Nullable
			WorkJob w = next();
			if (w == null)
				break;
			w.run(this);
		}
	}

	@Override
	@Nullable
	public WorkJob next() {
		Iterator<WorkList> workListIterator = jobs.iterator();
		while (true) {
			if (workListIterator.hasNext()) {
				WorkList workList = workListIterator.next();
//			for (WorkList workList :jobs) {
				if (!workList.isDone()) {
					for (WorkJob w : workList.getJobs()) {
						if (!w.isDone())
							return w;
					}
					workList.setDone();
				} else {
					workListIterator.remove();
					doneWork.add(workList);
					return next();
				}
			} else
				return null;
		}
//		return null;
	}

	@Override
	public int totalSize() {
		int totalSize = 0;
		for (WorkList job : jobs) {
			totalSize += job.getJobs().size();
		}
		return totalSize;
	}
}

//
//
//
