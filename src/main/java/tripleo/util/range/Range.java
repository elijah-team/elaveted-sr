/*
 * Tripleo-Range, copyright Tripleo <oluoluolu+range-impl@gmail.com>
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package tripleo.util.range;

import java.util.*;

/**
 * Created 9/18/20 7:25 AM
 */
public class Range implements Iterable<Integer> {
//	final static RangeImpl end = new RangeEndImpl();

	//RangeImpl first = end;
	LinkedList<RangeImpl> list = new LinkedList<RangeImpl>();

	public Range() {

	}

	public Range(int start, int finish) {
		RangeContinuousImpl impl = new RangeContinuousImpl(start, finish);
//		impl.setNext(end);
		//first = impl;
		list.add(impl);
	}

	public void add(int single) {
		_add(single);
		coalesce();
	}

	public void _add(int single) {
		boolean added=false;
		RangeSingleImpl impl = new RangeSingleImpl(single);
//		impl.setNext(end);
		for (int i = 0; i < list.size(); i++) {
			RangeImpl iter = list.get(i);
			if (iter instanceof RangeSingleImpl) {
				if ((single >= iter.getLowest()) && single <= iter.getHighest()) {
					// do nothing
					added = true;
				} else if (single < iter.getLowest()) {
					if (i == 0) {
						if (single-1 == iter.getLowest())
							iter.setLowest(single-1);
						else {
							RangeImpl rsi = new RangeSingleImpl(single);
							list.add(0, rsi);
							added = true;
						}

					}
				}
			} else if (iter instanceof RangeContinuousImpl) {
//				System.out.println("707");
			} else
				throw new IllegalStateException("cant be here");

		}
		if (added == false) {
			list.add(impl);
		}
	}

	public void coalesce() {
		if (list.size() < 2) return;
		if (false) {
			LinkedList<RangeImpl> list2 = new LinkedList<RangeImpl>();
			boolean changed = false;
			//
			for (int i = 0; i < list.size()-1; i++) {
				if (list.get(i).getHighest() > list.get(i + 1).getLowest()
				||  list.get(i).getHighest()+1 == list.get(i + 1).getLowest()) {
					list2.add(mk_nod(list.get(i).getLowest(), list.get(i + 1).getHighest()));
					++i;
					changed = true;
				}
			}
			if (list.size()>0 && list2.size()>0)
				if (list.get(list.size()-1).getLowest() > list2.get(list2.size()-1).getHighest()) {
					list2.add(mk_nod(list2.get(list2.size() - 1).getLowest(), list.get(list.size() - 1).getHighest()));
					list2.remove(list2.size() - 2);
				}
			if (changed)
				list = list2;
		} else {
			int removals = 0;
			while (true) {
				ListIterator<RangeImpl> iter = list.listIterator();
				RangeImpl prev = iter.next(), next;
				while (iter.hasNext()) {
					next = iter.next();
//					System.out.println("705 " + prev + " " + next);
					if (next.getLowest() < prev.getHighest()) {
						if (prev.getHighest() <= next.getHighest()) {
							((RangeContinuousImpl) prev).setFinish(next.getHighest());
							iter.remove();
							removals++;
						} else {
							iter.remove();
							removals++;
						}
					} else if (next.getLowest() == prev.getHighest()+1) {
						if (prev.getHighest() <= next.getHighest()) {
							((RangeContinuousImpl) prev).setFinish(next.getHighest());
							iter.remove();
							removals++;
						}
					} else {
//						System.out.println("704 "+prev+" "+next);
/*
						// out of order
						list.remove(iter.previousIndex());
//						list.remove(iter.previousIndex());
						list.add(next);
						list.add(prev);
*/
					}
					prev = list.get(iter.previousIndex());//next;
				}
				if (removals == 0) break;
				removals = 0;
			}
		}
	}

	public static RangeImpl mk_nod(int low, int high) {
		if (low == high)
			return new RangeSingleImpl(low);
		else
			return new RangeContinuousImpl(low, high);
	}

	public void add(int start, int finish) {
		_add(start, finish);
		coalesce();
	}

	public void _add(int start, int finish) {
		boolean added = false;
		RangeImpl cand = null;
		for (int i = 0; i < list.size(); i++) {
			RangeImpl iter = list.get(i);
			if (iter instanceof RangeSingleImpl) {
				if ((start >= iter.getLowest()) && start <= iter.getHighest()) {
					// do nothing
					added = true;
				} else if (start < iter.getLowest()) {
					if (i == 0) {
						if (start-1 == iter.getLowest())
							iter.setLowest(start-1);
						else {
							RangeImpl rsi = new RangeContinuousImpl(start, finish);
							list.add(0, rsi);
							added = true;
						}

					}
				} else
					added = false;
			} else if (iter instanceof RangeContinuousImpl) {
				if ((start >= iter.getLowest()) && finish <= iter.getHighest()) {
					// do nothing
				} else if (start < iter.getLowest()) {
					if (i == 0) {
						if (start-1 == iter.getLowest())
							iter.setLowest(start-1);
						else {
							RangeImpl rsi = new RangeSingleImpl(start);
							list.add(0, rsi);
						}

					}
				} else if (start > iter.getHighest()) {
					cand = iter;
				}
			} else
				throw new IllegalStateException("cant be here");
		}
		if (added == false) {
			if (cand == null) {
				RangeContinuousImpl impl = new RangeContinuousImpl(start, finish);
				list.add(impl);
			} else {
				RangeImpl rsi = new RangeContinuousImpl(start, finish);
				list.add(list.indexOf(cand)+1, rsi);
			}
		}
	}

	public void remove(int single) {
		_remove(single);
		coalesce();
	}

	public void _remove(int single) {
		RangeImpl iter;
		for (int i = 0; i < list.size(); i++) {
			iter = list.get(i);
			if (iter instanceof RangeContinuousImpl) {
				RangeContinuousImpl rci = (RangeContinuousImpl) iter;
				if (single >= rci.getLowest() && single <= rci.getHighest()) {
//					int y=22;
//					System.out.println("706 "+single+" "+rci);
					if (single == rci.getLowest()) {
						rci.setLowest(single+1);
					} else if (single == rci.getHighest()) {
						rci.setFinish(single-1);
					} else {
						RangeImpl rci1 = mk_nod(rci.getLowest(), single-1);
						RangeImpl rci2 = mk_nod(single+1, rci.getHighest());
						list.remove(i);
						list.add(i, rci1);
						list.add(i+1, rci2);
						break;
					}
				} //else
					//throw new NotImplementedException();
			} else if (iter instanceof RangeSingleImpl) {
				RangeSingleImpl rsi = (RangeSingleImpl) iter;
				if (single == rsi.getLowest())
					list.remove(i);
			} else
				throw new IllegalStateException("cant be here");
		}
	}

	public void remove(int start, int finish) {
		_remove(start, finish);
		coalesce();
	}

	public void _remove(int start, int finish) {
		RangeImpl iter;
//		System.out.println("705 "+list);
		for (int i = 0; i < list.size(); i++) {
			iter = list.get(i);
			if (iter instanceof RangeContinuousImpl) {
				RangeContinuousImpl rci = (RangeContinuousImpl) iter;
				if (start >= rci.getLowest() && start <= rci.getHighest()) {
//					System.out.println("706 " + start + " " + finish);
					if (start == rci.getLowest()) {
						rci.setLowest(start + 1);
					} else if (start == rci.getHighest()) {
						rci.setFinish(start - 1);
					} else {
						RangeImpl rci1 = mk_nod(rci.getLowest(), start-1);
						RangeImpl rci2 = mk_nod(finish+1, rci.getHighest());
						if (rci2.getLowest() > rci2.getHighest()) {
							rci2 = mk_nod(finish + 1, list.get(i + 1).getHighest());
							list.remove(i);
						}
						list.remove(i);
						list.add(i, rci1);
						list.add(i+1, rci2);
						break;
					}
				} else {
					RangeImpl rci1 = mk_nod(rci.getLowest(), Math.min(start-1, rci.getHighest()));
					RangeImpl rci2 = mk_nod(finish+1, rci.getHighest());
					if (rci2.getLowest() > rci2.getHighest()) {
						rci2 = mk_nod(finish + 1, list.get(i + 1).getHighest());
//						System.out.println("709 "+i);
						list.remove(i);
						list.remove(i);
						list.add(i, rci1);
						list.add(i + 1, rci2);
//						++i;
					} else {
						list.remove(i);
						list.add(i, rci1);
						list.add(i + 1, rci2);
					}
//					System.out.println("708 rci1 "+rci1);
//					System.out.println("708 rci2 "+rci2);
					break;
				}
			} else if (iter instanceof RangeSingleImpl) {
				RangeSingleImpl rsi = (RangeSingleImpl) iter;
				if (start == rsi.getLowest())
					list.remove(i);
			} else
				throw new IllegalStateException("cant be here");
		}
//		System.out.println("607 "+list);
	}

	public String lispRepr() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			RangeImpl iter = list.get(i);
//			if (iter == end) break;
			if (iter instanceof RangeContinuousImpl) {
				sb.append(String.format("(c %d %d)", iter.getLowest(), iter.getHighest()));
			} else if (iter instanceof RangeSingleImpl) {
				sb.append(String.format("(s %d)", iter.getLowest()));
			} else
				throw new IllegalStateException("cant be here");
		}
		sb.append("(end)");
		return sb.toString();
	}

	@Override
	public Iterator<Integer> iterator() {
		return new Iterator<Integer>() {
			int i = 0, ii;

			@Override
			public boolean hasNext() {
				if (i+1 >= list.size() && i+1 <= list.size()) {} else return false;

				final RangeImpl range = list.get(i);

				if (range instanceof RangeContinuousImpl) {
					if (ii == range.getHighest()) {
						int R = range.getHighest();
						i++; ii = 0;
						return hasNext();
					}
					return true;
				} else if (range instanceof RangeSingleImpl) {
					i++; ii = 0;
					return true;
				} else
					throw new IllegalStateException("can't be here");
			}

			@Override
			public Integer next() {
				if (!(list.size() < i)) {
					final RangeImpl range = list.get(i);

//					System.out.println("600 " + list);

					if (range instanceof RangeContinuousImpl) {
						if (ii == range.getHighest()) {
							int R = range.getHighest();
							i++; ii = 0;
							return R;
						}
						return range.getLowest() + ii++;
					} else if (range instanceof RangeSingleImpl) {
						int R = range.getLowest();
						i++; ii = 0;
						return R;
					} else
						throw new IllegalStateException("can't be here");

				}
				throw new IndexOutOfBoundsException(""+list.size()+" "+i);
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException("doesn't support remove right now.");
			}
		};
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Integer integer : this) {
			sb.append(integer);
			sb.append(", ");
		}
		return sb.toString();
	}

	public boolean has(int v) {
		ListIterator<RangeImpl> x = list.listIterator();
		while (x.hasNext()) {
			RangeImpl r = x.next();
			if (v >= r.getLowest() && v <= r.getHighest())
				return true;
		}
		return false;
	}
}

//
//
//
