/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.cglib.dx.ssa;

import java.util.HashMap;
import java.util.List;

import com.android.cglib.dx.rop.code.RegisterSpec;
import com.android.cglib.dx.rop.code.RegisterSpecSet;
import com.android.cglib.dx.util.MutabilityControl;

/**
 * Container for local variable information for a particular {@link
 * SsaMethod}.
 * Stolen from {@link com.android.cglib.dx.rop.code.LocalVariableInfo}.
 */
public class LocalVariableInfo         extends MutabilityControl {
    /** {@code >= 0;} the register count for the method */
    private final int regCount;

    /**
     * {@code non-null;} {@link RegisterSpecSet} to use when indicating a block
     * that has no locals; it is empty and immutable but has an appropriate
     * max size for the method
     */
    private final RegisterSpecSet emptySet;

    /**
     * {@code non-null;} array consisting of register sets representing the
     * sets of variables already assigned upon entry to each block,
     * where array indices correspond to block indices
     */
    private final RegisterSpecSet[] blockStarts;

    /** {@code non-null;} map from instructions to the variable each assigns */
    private final HashMap<SsaInsn, RegisterSpec> insnAssignments;

    /**
     * Constructs an instance.
     *
     * @param method {@code non-null;} the method being represented by this instance
     */
    public LocalVariableInfo(SsaMethod method) {
        if (method == null) {
            throw new NullPointerException("method == null");
        }

        List<SsaBasicBlock> blocks = method.getBlocks();

        this.regCount = method.getRegCount();
        this.emptySet = new RegisterSpecSet(regCount);
        this.blockStarts = new RegisterSpecSet[blocks.size()];
        this.insnAssignments =
            new HashMap<SsaInsn, RegisterSpec>(/*hint here*/);

        emptySet.setImmutable();
    }

    /**
     * Sets the register set associated with the start of the block with
     * the given index.
     *
     * @param index {@code >= 0;} the block index
     * @param specs {@code non-null;} the register set to associate with the block
     */
    public void setStarts(int index, RegisterSpecSet specs) {
        throwIfImmutable();

        if (specs == null) {
            throw new NullPointerException("specs == null");
        }

        try {
            blockStarts[index] = specs;
        } catch (ArrayIndexOutOfBoundsException ex) {
            // Translate the exception.
            throw new IllegalArgumentException("bogus index");
        }
    }

    /**
     * Merges the given register set into the set for the block with the
     * given index. If there was not already an associated set, then this
     * is the same as calling {@link #setStarts}. Otherwise, this will
     * merge the two sets and call {@link #setStarts} on the result of the
     * merge.
     *
     * @param index {@code >= 0;} the block index
     * @param specs {@code non-null;} the register set to merge into the start set
     * for the block
     * @return {@code true} if the merge resulted in an actual change
     * to the associated set (including storing one for the first time) or
     * {@code false} if there was no change
     */
    public boolean mergeStarts(int index, RegisterSpecSet specs) {
        RegisterSpecSet start = getStarts0(index);
        boolean changed = false;

        if (start == null) {
            setStarts(index, specs);
            return true;
        }

        RegisterSpecSet newStart = start.mutableCopy();
        newStart.intersect(specs, true);

        if (start.equals(newStart)) {
            return false;
        }

        newStart.setImmutable();
        setStarts(index, newStart);

        return true;
    }

    /**
     * Gets the register set associated with the start of the block
     * with the given index. This returns an empty set with the appropriate
     * max size if no set was associated with the block in question.
     *
     * @param index {@code >= 0;} the block index
     * @return {@code non-null;} the associated register set
     */
    public RegisterSpecSet getStarts(int index) {
        RegisterSpecSet result = getStarts0(index);

        return (result != null) ? result : emptySet;
    }

    /**
     * Gets the register set associated with the start of the given
     * block. This is just convenient shorthand for
     * {@code getStarts(block.getLabel())}.
     *
     * @param block {@code non-null;} the block in question
     * @return {@code non-null;} the associated register set
     */
    public RegisterSpecSet getStarts(SsaBasicBlock block) {
        return getStarts(block.getIndex());
    }

    /**
     * Gets a mutable copy of the register set associated with the
     * start of the block with the given index. This returns a
     * newly-allocated empty {@link RegisterSpecSet} of appropriate
     * max size if there is not yet any set associated with the block.
     *
     * @param index {@code >= 0;} the block index
     * @return {@code non-null;} the associated register set
     */
    public RegisterSpecSet mutableCopyOfStarts(int index) {
        RegisterSpecSet result = getStarts0(index);

        return (result != null) ?
            result.mutableCopy() : new RegisterSpecSet(regCount);
    }

    /**
     * Adds an assignment association for the given instruction and
     * register spec. This throws an exception if the instruction
     * doesn't actually perform a named variable assignment.
     *
     * <b>Note:</b> Although the instruction contains its own spec for
     * the result, it still needs to be passed in explicitly to this
     * method, since the spec that is stored here should always have a
     * simple type and the one in the instruction can be an arbitrary
     * {@link com.android.cglib.dx.rop.type.TypeBearer} (such as a constant value).
     *
     * @param insn {@code non-null;} the instruction in question
     * @param spec {@code non-null;} the associated register spec
     */
    public void addAssignment(SsaInsn insn, RegisterSpec spec) {
        throwIfImmutable();

        if (insn == null) {
            throw new NullPointerException("insn == null");
        }

        if (spec == null) {
            throw new NullPointerException("spec == null");
        }

        insnAssignments.put(insn, spec);
    }

    /**
     * Gets the named register being assigned by the given instruction, if
     * previously stored in this instance.
     *
     * @param insn {@code non-null;} instruction in question
     * @return {@code null-ok;} the named register being assigned, if any
     */
    public RegisterSpec getAssignment(SsaInsn insn) {
        return insnAssignments.get(insn);
    }

    /**
     * Gets the number of assignments recorded by this instance.
     *
     * @return {@code >= 0;} the number of assignments
     */
    public int getAssignmentCount() {
        return insnAssignments.size();
    }

    public void debugDump() {
        for (int index = 0 ; index < blockStarts.length; index++) {
            if (blockStarts[index] == null) {
                continue;
            }

            if (blockStarts[index] == emptySet) {
                System.out.printf("%04x: empty set\n", index);
            } else {
                System.out.printf("%04x: %s\n", index, blockStarts[index]);
            }
        }
    }

    /**
     * Helper method, to get the starts for a index, throwing the
     * right exception for range problems.
     *
     * @param index {@code >= 0;} the block index
     * @return {@code null-ok;} associated register set or {@code null} if there
     * is none
     */
    private RegisterSpecSet getStarts0(int index) {
        try {
            return blockStarts[index];
        } catch (ArrayIndexOutOfBoundsException ex) {
            // Translate the exception.
            throw new IllegalArgumentException("bogus index");
        }
    }
}
