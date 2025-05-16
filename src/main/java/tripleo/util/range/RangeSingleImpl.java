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

/**
 * Created 9/18/20 7:30 AM
 */
public class RangeSingleImpl implements RangeImpl {
	int single;
	RangeImpl next;

	public RangeSingleImpl(int single) {
		this.single = single;
	}

	@Override
	public RangeImpl getNext() {
		return next;
	}

	@Override
	public void setNext(RangeImpl next) {
		this.next = next;
	}

	@Override
	public int getHighest() {
		return single;
	}

	@Override
	public int getLowest() {
		return single;
	}

	@Override
	public void setLowest(int single) {
		this.single = single;
	}

	@Override
	public String toString() {
		return "RangeSingleImpl{" +
				"single=" + single +
				", next=" + next +
				'}';
	}
}

//
//
//
