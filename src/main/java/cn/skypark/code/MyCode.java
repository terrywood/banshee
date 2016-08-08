package cn.skypark.code;


import java.util.HashMap;

public  class MyCode {
    int iLast = -20;
    private HashMap<Integer, String> mapCode = new HashMap();

    MyCode() {
    }

    public void addValue(int iX, String value) {
        int iTmp = iX - this.iLast;
        if(iTmp < 0) {
            iTmp = -iTmp;
        }

        if(iTmp < 10) {
            String sLast = (String)this.mapCode.get(Integer.valueOf(this.iLast));
            if(value == "V" && sLast == "v") {
                this.mapCode.remove(Integer.valueOf(this.iLast));
                this.iLast = -20;
            }

            if(value == "v" && sLast == "V") {
                return;
            }

            if(value == "E" && sLast == "L") {
                this.mapCode.remove(Integer.valueOf(this.iLast));
                this.iLast = -20;
            }

            if(value == "L" && sLast == "E") {
                return;
            }

            if(value == "E" && sLast == "F") {
                this.mapCode.remove(Integer.valueOf(this.iLast));
                this.iLast = -20;
            } else {
                if(value == "F" && sLast == "E") {
                    return;
                }

                if(value == "m" && sLast == "n") {
                    this.mapCode.remove(Integer.valueOf(this.iLast));
                    this.iLast = -20;
                } else {
                    if(value == "n" && sLast == "m") {
                        return;
                    }

                    if(value == "h" && sLast == "n") {
                        this.mapCode.remove(Integer.valueOf(this.iLast));
                        this.iLast = -20;
                    } else {
                        if(value == "n" && sLast == "h") {
                            return;
                        }

                        if(sLast == "i") {
                            this.mapCode.remove(Integer.valueOf(this.iLast));
                            this.iLast = -20;
                        } else {
                            if(value == "i") {
                                return;
                            }

                            if(sLast == "r") {
                                this.mapCode.remove(Integer.valueOf(this.iLast));
                                this.iLast = -20;
                            } else {
                                if(value == "r") {
                                    return;
                                }

                                if(sLast == "L") {
                                    this.mapCode.remove(Integer.valueOf(this.iLast));
                                    this.iLast = -20;
                                } else if(value == "L") {
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }

        this.mapCode.put(Integer.valueOf(iX), value);
        this.iLast = iX;
    }

    public String getValue() {
        String sRet = "";

        for(int i = 0; i < 100; ++i) {
            if(this.mapCode.containsKey(Integer.valueOf(i))) {
                sRet = sRet + (String)this.mapCode.get(Integer.valueOf(i));
            }
        }

        return sRet;
    }
}