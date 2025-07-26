local json = {}
local unpack  = unpack or table.unpack
local tconcat = table.concat

--===============================================
-- Encode
--===============================================
local encode_types -- table

local escape_map = {
    ['"']  = '\\"',
    ['\\'] = '\\\\',
    ['\b'] = '\\b',
    ['\f'] = '\\f',
    ['\n'] = '\\n',
    ['\r'] = '\\r',
    ['\t'] = '\\t',
}

local function escape_string(s)
    return s:gsub('[%c\\"]', escape_map)
end

local function is_array(t)
    local max = 0
    for k in pairs(t) do
        if type(k) ~= "number" or k % 1 ~= 0 or k < 1 then
            return false
        end
        if k > max then max = k end
    end
    return max
end

-- encoding
local encode_number = function(value)
    return tostring(value)
end

local encode_string = function(value)
    return '"' .. escape_string(value) .. '"'
end

local encode_boolean = function(value)
    return value and "true" or "false"
end

local encode_table = function(value)
    local len = is_array(value)
    local et = encode_types
    if len then
        local parts = {}
        for i = 1, len do
            local v = value[i]
            parts[i] = et[type(v)](v)
        end
        return tconcat({ "[", tconcat(parts, ","), "]" })
    end
    
    local parts = {}
    local i = 1
    for k, v in pairs(value) do
        if type(k) == "string" then
            parts[i] = tconcat({ '"', escape_string(k), '":', et[type(v)](v) })
            i = i + 1
        end
    end
    return tconcat({ "{",tconcat(parts, ","), "}" })
end

local encode_nil = function()
    return "null"
end

encode_types = {
	number  = encode_number,
	string  = encode_string,
	boolean = encode_boolean,
	table   = encode_table,
	["nil"] = encode_nil,
	
	["function"] = function() error("Unsupported type: function") end,
	["userdata"] = function() error("Unsupported type: function") end,
	["thread"] = function() error("Unsupported type: function") end
}

json.encode = function(value)
    local t = type(value)
    return encode_types[t](value)
end

--===============================================
-- Prettify
--===============================================
local prettify_types -- table

local prettify_table = function(value, tab, level, isarray)
    local pt = prettify_types
    local T = isarray and "\n" .. tab:rep(level) or ""
    
    local len = is_array(value)
    if len then
        local t
        local parts = {}
        for i = 1, len do
            local v = value[i]
            t = type(v)
            parts[i] = pt[t](v, tab, level+1, true)
        end
        local T2 = ""
        if t == "table" then
            T2 = "\n" .. tab:rep(level)
        end
        return tconcat({T, "[", tconcat(parts, ", "), T2, "]" })
    end
    
    local parts = {}
    local i = 1
    for k, v in pairs(value) do
        if type(k) == "string" then
            parts[i] = tconcat({tab, '"', escape_string(k), '": ', pt[type(v)](v, tab, level+1) })
            i = i + 1
        end
    end
    return tconcat({ "{\n", tab:rep(level), tconcat(parts, ",\n" .. tab:rep(level)), "\n", tab:rep(level), "}" })
end

prettify_types = {
	table   = prettify_table,
	
	number  = encode_number,
	string  = encode_string,
	boolean = encode_boolean,
	["nil"] = encode_nil,
	
	["function"] = function() error("Unsupported type: function") end,
	["userdata"] = function() error("Unsupported type: function") end,
	["thread"] = function() error("Unsupported type: function") end
}

json.prettify = function(value, tab)
    tab = tab or "  "
    local t = type(value)
    return prettify_types[t](value, tab, 0)
end

--===============================================
-- Decode
--===============================================
local decode_types -- table

local back_escape_map = {
    ['\\"']  = '"',
    ['\\\\'] = '\\',
    ['\\b']  = '\b',
    ['\\f']  = '\f',
    ['\\n']  = '\n',
    ['\\r']  = '\r',
    ['\\t']  = '\t',
}

local function back_escape_string(s)
    return s:gsub('[\\][bfnrt"\\]', back_escape_map)
end

-- decodes
local decode_number = function(value, pos)
    local _, p = value:find("[-]?%d+", pos)
    return tonumber(value:sub(pos, p)), p+1
end

local decode_string = function(value, pos)
    local p = pos
    local lp1, lp2
    
    while true do
        lp1, lp2 = value:find('\\*"', p+1)
        p = lp2
        if (lp1-lp2)%2 == 0 then break end
    end
    
    local v = value:sub(pos+1, p-1)
    return back_escape_string(v), p+1
end

local decode_boolean = function(value, pos)
    local v = value:sub(pos, pos)
    if v == "t" then
        return true, pos+4
    end
    return false, pos+5
end

local decode_nil = function(value, pos)
    return nil, pos+4
end

local decode_object = function(value, pos, lenv)
    local t = {}
    local iskey = false
    local key = ""
    
    local i = pos+1
    while i <= lenv do
        local p = value:find("[-0-9tfn%[%{%}\"]", i)
        local v = value:sub(p, p)
        
        if v == "}" then return t, p+1 end
        
        iskey = not iskey
        
        if iskey then
            key, i = decode_string(value, p)
        else
            t[key], i = decode_types[v](value, p, lenv)
        end
    end
end

local decode_array = function(value, pos, lenv)
    local t = {}
    local tlen = 0
    
    local i = pos+1
    while i <= lenv do
        local p = value:find("[-0-9tfn%[%{%]\"]", i)
        local v = value:sub(p, p)
        
        if v == "]" then return t, p+1 end
        
        tlen = tlen+1
        t[tlen], i = decode_types[v](value, p, lenv)
    end
end

decode_types = {
	['{'] = decode_object,
	['['] = decode_array,
	['t'] = decode_boolean,
	['f'] = decode_boolean,
	['n'] = decode_nil,
	['"'] = decode_string,
	['-'] = decode_number,
	['0'] = decode_number,
	['1'] = decode_number,
	['2'] = decode_number,
	['3'] = decode_number,
	['4'] = decode_number,
	['5'] = decode_number,
	['6'] = decode_number,
	['7'] = decode_number,
	['8'] = decode_number,
	['9'] = decode_number
}

json.decode = function(value)
    local t = {}
    local tlen = 0
    
    local lenv = #value
    
    local i = 1
    while i <= lenv do
        local p = value:find("[-0-9tfn%[%{\"]", i)
        if not p then break end
        
        local v = value:sub(p, p)
        
        tlen = tlen+1
        t[tlen], i = decode_types[v](value, p, lenv)
    end
    
    return unpack(t)
end

return json